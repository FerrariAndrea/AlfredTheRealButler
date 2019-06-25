# -------------------------------------------------------------
# led25GpioTurnOff.sh
# Key-point: we can manage a device connected on a GPIO pin by
# using the GPIO shell library. 
# The pin 25 is physical 37 and Wpi 25.
#	sudo bash led25GpioTurnOff.sh
# -------------------------------------------------------------
gpio mode 4 out #
gpio write 4 0 #