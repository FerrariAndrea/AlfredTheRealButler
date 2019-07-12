import serial
ser = serial.Serial('/dev/ttyUSB0', 115200)  # open serial port
ser.flushInput()
print(ser.name)         # check which port was really used

linein = ser.readline()     # read a string
print(linein)
linein = ser.readline()     # read a string
print(linein)
linein = ser.readline()     # read a string
print(linein)

ser.write("w")     # write a string
ser.close()             # close port


# sudo apt-get install python-serial
# sudo pip install pyserial
# ls /dev/tty*