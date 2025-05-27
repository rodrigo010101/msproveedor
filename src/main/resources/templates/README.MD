#Probar puertos
Test-NetConnection -ComputerName 127.0.0.1 -Port 8080

netstat -a -n -o
netstat -a -n | findstr LISTENING