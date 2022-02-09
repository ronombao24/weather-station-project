#GENERIC IMPORTS
import os
import time
import board
import busio
import digitalio
import pyrebase
import random

#SENSOR IMPORTS
import adafruit_lps2x
import adafruit_bmp280
import adafruit_sgp30
import adafruit_ahtx0

def firebase_init():
    #FIREBASE CONFIG
    config = {
        "apiKey" : "iF4wAXdjSQGAJiu11cqAlIkd3vHs95I0Psa6fw0w",
        "authDomain": "ceng317weatherstationproject.firebaseapp.com",
        "databaseURL" : "https://ceng317weatherstationproject-default-rtdb.firebaseio.com/",
        "storageBucket" : "ceng317weatherstationproject.appspot.com"
    }

    #DECLARE FIREBASE OBJECT
    firebase = pyrebase.initialize_app(config)
    database = firebase.database()
    return database

def deviceID_init():
    if not os.path.isdir("/home/pi/HWS"):
        os.mkdir("/home/pi/HWS")
    os.chdir("/home/pi/HWS")
    if not os.path.isfile("device_ID"):
        fp = open("device_ID", 'w')
        device_ID = hex(random.randint(0, 4294967295))[2:].rjust(8, '0').upper()
        print("New ID: " + device_ID)
        fp.write(device_ID)
        fp.close()
    fp = open("device_ID", 'r')
    return fp.read()

def sensor_init():
    #SENSOR DECLARATIONS
    sensor_array = {"lps": [False, 2], "bmp": [False, 3], "sgp": [False, 4], "aht": [False, 5]}

    i2c = busio.I2C(board.SCL, board.SDA)
    try:
        lps22 = adafruit_lps2x.LPS22(i2c)
        sensor_array["lps"].append(lps22)
    except:
        print("LPS22 missing or damaged")
        sensor_array["lps"][0] = True
    try:
        bmp280 = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
        sensor_array["bmp"].append(bmp280)
    except:
        print("BMP280 missing or damaged")
        sensor_array["bmp"][0] = True
    try:
        sgp30 = adafruit_sgp30.Adafruit_SGP30(i2c)
        sensor_array["sgp"].append(sgp30)
    except:
        print("SGP30 missing or damaged")
        sensor_array["sgp"][0] = True
    try:
        aht20 = adafruit_ahtx0.AHTx0(i2c)
        sensor_array["aht"].append(aht20)
    except:
        print("AHT20 missing or damaged")
        sensor_array["aht"][0] = True

    print("I2C addresses found:",[hex(device_address) for device_address in i2c.scan()],)
    return sensor_array

def led_init():
    #LED CIRCUIT SETUP
    print("Setting up GPIO17 for the LED")
    led = digitalio.DigitalInOut(board.D17)
    led.direction = digitalio.Direction.OUTPUT
    led.value = False
    return led

def get_readings():
    enviro_readings = {"pressure": 0, "temperature": 999, "CO2": 0, "humidity": 0}
    
    #DECLARE READINGS
    if not sensor_array["lps"][0]:
        enviro_readings["pressure"] = float("{:.3f}".format(sensor_array["lps"][2].pressure))
    if not sensor_array["bmp"][0]:
        enviro_readings["temperature"] = float("{:.2f}".format(sensor_array["bmp"][2].temperature))
    if not sensor_array["sgp"][0]:
        enviro_readings["CO2"] = float("{:.2f}".format(sensor_array["sgp"][2].eCO2))
    if not sensor_array["aht"][0]:
        enviro_readings["humidity"] = float("{:.2f}".format(sensor_array["aht"][2].relative_humidity))
    return enviro_readings

def upload_readings(db, enviro_readings, device_ID):
    #CURRENT READINGS
    db.child("Weather Station Reading").child(device_ID).child("current_readings").child("current_pressure").set(enviro_readings["pressure"])
    db.child("Weather Station Reading").child(device_ID).child("current_readings").child("current_temperature").set(enviro_readings["temperature"])
    db.child("Weather Station Reading").child(device_ID).child("current_readings").child("current_CO2").set(enviro_readings["CO2"])
    db.child("Weather Station Reading").child(device_ID).child("current_readings").child("current_humidity").set(enviro_readings["humidity"])
    
    #HISTORICAL READINGS
    timestamp = time.strftime("%d%b%Y_%H:%M:%S", time.localtime())
    db.child("Weather Station Reading").child(device_ID).child("historical_readings").child(timestamp).child("pressure_reading").set(enviro_readings["pressure"])
    db.child("Weather Station Reading").child(device_ID).child("historical_readings").child(timestamp).child("temperature_reading").set(enviro_readings["temperature"])
    db.child("Weather Station Reading").child(device_ID).child("historical_readings").child(timestamp).child("CO2_reading").set(enviro_readings["CO2"])
    db.child("Weather Station Reading").child(device_ID).child("historical_readings").child(timestamp).child("humidity_reading").set(enviro_readings["humidity"])

def print_readings(enviro_readings):
    #PRINT
    print("Atmospheric Pressure: {} hPa". format(enviro_readings["pressure"]))
    print("Temperature: {} C". format(enviro_readings["temperature"]))
    print("CO2 (Air Quality) Value: {} ppm". format(enviro_readings["CO2"]))
    print("Humidity: {} %rH". format(enviro_readings["humidity"]))

def blink_led(status_array, led):
    #STATUS LED
    inverted = False
    
    if all(status_array[sensor_name][0] for sensor_name in status_array):
        num_blinks = 1
        inverted = True
    else:
        for sensor_name in status_array:
            if status_array[sensor_name][0]:
                num_blinks = status_array[sensor_name][1]
                break
            else:
                num_blinks = 1
        
    off_time = 3.0
    i = 0
    while i < num_blinks:
        led.value = not inverted
        time.sleep(0.2)
        led.value = inverted
        time.sleep(0.2)
        off_time -= 0.4
        i += 1
    time.sleep(off_time)

def firebase_error(led):
    print("Error connecting to firebase")
    while True:
        led.value = True
        time.sleep(0.2)
        led.value = False
        time.sleep(0.2)
        led.value = True
        time.sleep(0.5)
        led.value = False
        time.sleep(0.6)

led = led_init()
try:
    db = firebase_init()
except:
    firebase_error(led)
dID = deviceID_init()
sensor_array = sensor_init()

#THE MAIN FUNCTION
try:
    print("Initiating Readings...")
    while True:
        enviro_readings = get_readings()
        try:
            upload_readings(db, enviro_readings, dID)
        except:
            firebase_error(led)
        print_readings(enviro_readings)

        blink_led(sensor_array, led)

except KeyboardInterrupt:
    print("\nProgram is shut off by CTRL+C command. Cleaning all GPIO connections.\n")
    led.value = False
    
except:
    print("\nUnknown Error")
    while True:
        led.value = True
        time.sleep(0.5)
        led.value = False
        time.sleep(0.5)
