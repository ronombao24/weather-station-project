#GENERIC IMPORTS
import time
import board
import busio
import digitalio
import pyrebase

#SENSOR IMPORTS
import adafruit_lps2x
import adafruit_bmp280
import adafruit_sgp30
import adafruit_ahtx0

#FIREBASE CONFIG
config = {
    "apiKey" : "iF4wAXdjSQGAJiu11cqAlIkd3vHs95I0Psa6fw0w",
    "authDomain": "ceng317weatherstationproject.firebaseapp.com",
    "databaseURL" : "https://ceng317weatherstationproject-default-rtdb.firebaseio.com/",
    "storageBucket" : "ceng317weatherstationproject.appspot.com"
}

#DECLARE FIREBASE OBJECT
firebase = pyrebase.initialize_app(config)
db = firebase.database()

#SENSOR DECLARATIONS
sensorMissing = {"lps": [False, 2], "bmp": [False, 3], "sgp": [False, 4], "aht": [False, 5]}

i2c = busio.I2C(board.SCL, board.SDA)
try:
    lps22 = adafruit_lps2x.LPS22(i2c)
except:
    print("LPS22 missing or damaged")
    sensorMissing["lps"][0] = True
try:
    bmp280 = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
except:
    print("BMP280 missing or damaged")
    sensorMissing["bmp"][0] = True
try:
    sgp30 = adafruit_sgp30.Adafruit_SGP30(i2c)
except:
    print("SGP30 missing or damaged")
    sensorMissing["sgp"][0] = True
try:
    aht20 = adafruit_ahtx0.AHTx0(i2c)
except:
    print("AHT20 missing or damaged")
    sensorMissing["aht"][0] = True

print("I2C addresses found:",[hex(device_address) for device_address in i2c.scan()],)

#LED CIRCUIT SETUP
print("Setting up GPIO17 for the LED")
led = digitalio.DigitalInOut(board.D17)
led.direction = digitalio.Direction.OUTPUT

#THE MAIN FUNCTION
try:
    print("Initiating Readings...")
    while True:
        #DECLARE READIINGS
        if sensorMissing["lps"][0]:
            enviro_pressure = 930.000
        else:
            enviro_pressure = float("{:.3f}".format(lps22.pressure))
        if sensorMissing["bmp"][0]:
            enviro_temp = 21.00
        else:
            enviro_temp = float("{:.2f}".format(bmp280.temperature))
        if sensorMissing["sgp"][0]:
            enviro_CO2 = 400.00
        else:
            enviro_CO2 = float("{:.2f}".format(sgp30.eCO2))
        if sensorMissing["aht"][0]:
            enviro_humidity = 15.00
        else:
            enviro_humidity = float("{:.2f}".format(aht20.relative_humidity))
        
        #DATA READINGS FOR INDIVIDUAL TESTING
        db.child("Weather Station Reading").child("pressure_reading").set(enviro_pressure)
        db.child("Weather Station Reading").child("temperature_reading").set(enviro_temp)
        db.child("Weather Station Reading").child("CO2_reading").set(enviro_CO2)
        db.child("Weather Station Reading").child("humidity_reading").set(enviro_humidity)

        #PRINT
        print("Atmospheric Pressure: {} hPa". format(enviro_pressure))
        print("Temperature: {} C". format(enviro_temp))
        print("CO2 (Air Quality) Value: {} ppm". format(enviro_CO2))
        print("Humidity: {} %rH". format(enviro_humidity))

        #STATUS LED
        for sensorName in sensorMissing:
            if sensorMissing[sensorName][0]:
                numBlinks = sensorMissing[sensorName][1]
                break
        else:
            numBlinks = 1
        
        offTime = 3.0
        i = 0
        while i < numBlinks:
            led.value = True
            time.sleep(0.2)
            led.value = False
            time.sleep(0.2)
            offTime -= 0.4
            i += 1
        time.sleep(offTime)

except KeyboardInterrupt:
    print("\nProgram is shut off by CTRL+C command. Cleaning all GPIO connections.\n")
    
except:
    print("\nUnknown Error")
    while True:
        led.value = True
        time.sleep(0.5)
        led.value = False
        time.sleep(0.5)