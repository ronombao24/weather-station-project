#GENERIC IMPORTS
import time
import board
import RPi.GPIO as GPIO
import pyrebase

#SENSOR IMPORTS
import adafruit_bmp280
#import adafruit_ahtx0
#import adafruit_lps2x
#import adafruit_sgp30

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

print("updated")
#SENSOR DECLARATIONS
i2c = board.I2C()
bmp280 = adafruit_bmp280.Adafruit_BMP280_I2C(i2c)
#aht20 = adafruit_ahtx0.AHTx0(i2c)
#lps22 = adafruit_lps2x.LPS22(i2c)
sgp30 = adafruit_sgp30.Adafruit_SGP30(i2c)

#LED CIRCUIT SETUP
led = 17
print("Setting up GPIO17 for the LED")
print("I2C addresses found:",[hex(device_address) for device_address in i2c.scan()],)
GPIO.setup(led, GPIO.OUT)

#THE MAIN FUNCTION
try:
    print("Initiating Readings...")
    while True:
        #DECLARE READIINGS
        enviro_temp = float("{:.2f}".format(bmp280.temperature))
        #enviro_humidity = float("{:.2f}".format(aht_x0.humidity))
        enviro_CO2 = float("{:.2f}".format(sgp30.eCO2))
        #enviro_pressure = float("{:.3f}".format(lps22.pressure))
        
        #FIREBASE DATA (FULL OBJECT)
        """
        data = {
        "temperature_reading": enviro_temp, #Temperature
        #humidity_reading": enviro_humidity, #Humidity
        "CO2_reading": enviro_CO2, #Air Quality
        #"pressure_reading": enviro_pressure, #Pressure
        }
        
        #FIREBASE PUSH ALL INFO
        db.child("Weather Station Reading").set(data)
        """
	#DATA READINGS FOR INDIVIDUAL TESTING
        db.child("Weather Station Reading").child("temperature_reading").set(enviro_temp)
        # db.child("Weather Station Reading").child("humidity_reading").set(enviro_humidity)
	db.child("Weather Station Reading").child("CO2_reading").set(enviro_CO2)
        # db.child("Weather Station Reading").child("pressure_reading").set(enviro_pressure)

        #PRINT
        print("Temperature: {} C". format(enviro_temp))
        GPIO.output(led,GPIO.HIGH)
        time.sleep(1.5)
        GPIO.output(led,GPIO.LOW)
        #print("Humidity: {} %rH". format(enviro_humidity))
        print("CO2 (Air Quality) Value: {} ppm". format(enviro_CO2))
        #print("Atmospheric Pressure: {} hPa". format(enviro_pressure))
        time.sleep(1.5)
except KeyboardInterrupt:
    print("\nProgram is shut off by CTRL+C command. Cleaning all GPIO connections.\n")
    
except:
    print ("\nOther exception error occured.\n")
    
finally:
    GPIO.cleanup()
