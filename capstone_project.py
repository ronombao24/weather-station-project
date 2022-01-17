#GENERIC IMPORTS
import time
import board
import busio
import digitalio
import pyrebase

#SENSOR IMPORTS
import adafruit_bmp280
import adafruit_ahtx0
import adafruit_lps2x
import adafruit_sgp30

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
bmpMissing = False
ahtMissing = False
lpsMissing = False
sgpMissing = False

i2c = busio.I2C(board.SCL, board.SDA)
try:
    bmp280 = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
except:
    print("BMP280 missing or damaged")
    bmpMissing = True
try:
    aht20 = adafruit_ahtx0.AHTx0(i2c)
except:
    print("AHT20 missing or damaged")
    ahtMissing = True
try:
    lps22 = adafruit_lps2x.LPS22(i2c)
except:
    print("LPS22 missing or damaged")
    lpsMissing = True
try:
    sgp30 = adafruit_sgp30.Adafruit_SGP30(i2c)
except:
    print("SGP30 missing or damaged")
    sgpMissing = True

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
        if bmpMissing:
            enviro_temp = 21.00
        else:
            enviro_temp = float("{:.2f}".format(bmp280.temperature))
        if ahtMissing:
            enviro_humidity = 15.00
        else:
            enviro_humidity = float("{:.2f}".format(aht20.relative_humidity))
        if sgpMissing:
            enviro_CO2 = 400.00
        else:
            enviro_CO2 = float("{:.2f}".format(sgp30.eCO2))
        if lpsMissing:
            enviro_pressure = 930.000
        else:
            enviro_pressure = float("{:.3f}".format(lps22.pressure))
        
        #DATA READINGS FOR INDIVIDUAL TESTING
        db.child("Weather Station Reading").child("temperature_reading").set(enviro_temp)
        db.child("Weather Station Reading").child("humidity_reading").set(enviro_humidity)
        db.child("Weather Station Reading").child("CO2_reading").set(enviro_CO2)
        db.child("Weather Station Reading").child("pressure_reading").set(enviro_pressure)

        #PRINT
        print("Temperature: {} C". format(enviro_temp))
        print("Humidity: {} %rH". format(enviro_humidity))
        print("CO2 (Air Quality) Value: {} ppm". format(enviro_CO2))
        print("Atmospheric Pressure: {} hPa". format(enviro_pressure))
        led.value = True
        time.sleep(1.5)
        led.value = False
        time.sleep(1.5)

except KeyboardInterrupt:
    print("\nProgram is shut off by CTRL+C command. Cleaning all GPIO connections.\n")
    
except:
    print("\nUnknown Error")
    while True:
        led.value = True
        time.sleep(0.5)
        led.value = False
        time.sleep(0.5)