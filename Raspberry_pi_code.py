import socket

import os

import gpio_code.gpio


CONFIG_FILE = dict()

FILE_DESCRIPTOR = dict()


CONFIG_FILE["BULB"] = os.path.join(os.path.expanduser("~"), "bulb.config")

CONFIG_FILE["FAN"] = os.path.join(os.path.expanduser("~"), "fan.config")


def execute(opts):

    if opts[0] in CONFIG_FILE.keys():

        fd = open(CONFIG_FILE[opts[0]], 'w')

        if opts[1] == "ON":

            fd.write('1')

        elif opts[1] == "OFF":

            fd.write('0')

        fd.close()

def gpio_con(opts):

    print "gpio_con executed"

    if opts == "1":

        os.system("echo  2  > /sys/class/gpio/export")

        print "pin exported"

        os.system("echo out > /sys/class/gpio/gpio2/direction")
        print "pinmode set"

        print "echo 1 > /sys/class/gpio/gpio2/value"

        os.system("echo 1 > /sys/class/gpio/gpio2/value")

    if opts == "2":

        os.system("echo 2 > /sys/class/gpio/export")

        os.system("echo out > /sys/class/gpio/gpio2/direction")

        os.system("echo 0 > /sys/class/gpio/gpio2/value")

    print "actuated"

def main():

    serverPort = 2000

    serverSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM)


    serverSocket.bind(('',serverPort))

    serverSocket.listen(1)

    connectionSocket, addr = serverSocket.accept()

    print 'The server is ready to receive'

    while 1:

        #connectionSocket, addr = serverSocket.accept()

	print 'connection accepted'

	#connectionSocket.send('hello\n')

	print 'message sent'

	print addr

        sentence =  connectionSocket.recv(1024)

	if sentence=='quit':

            connectionSocket.close()

	    break

	print sentence

        if sentence == "1_0":

           opts="2"

        elif sentence == "1_1":

           opts == "1"

        #if opts[1] == "ON":

           #opts[1] = "1"

        #elif opts[1] == "OFF":

           #opts[1] ="0"

        #if opts[0] == '2':

        gpio_con(opts)

        #execute(opts)

        #connectionSocket.send('Hello')


    serverSocket.close()

	

if __name__ == '__main__':

    main()
