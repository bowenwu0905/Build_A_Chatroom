# Build a Chatroom

## Features
### Server
Once the server starts up, it would be able to handle up to 10 clients connected at
a single time. It is responsible for listening for commands from the client as well
as sending messages to the client.

### *Class structure*
- Server: server class
- Server handler class handle messages received

### Client
The client will maintain the socket to listen for incoming messages from the server (public or private messages),
as well as listen to the UI (terminal) for messages from the user to send to the server.
The client would list all commands when the user types a ?

• logoff: sends a DISCONNECT_MESSAGE to the server (case-insensitive,must be stood alone on a line)
```
logoff
```

• who: sends a QUERY_CONNECTED_USERS to the server (case-insensitive,must be stood alone on a line)
```
who
```

• @user: sends a DIRECT_MESSAGE to the specified user to the server (case-insensitive,must be at the front of the command,the followed message can be empty)
```
@xxx hello
```

• @all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected(case-insensitive,must be at the front of the command when having `@all` in front)
```
@all logoff now
or
logoff now
```
• !user: sends a SEND_INSULT message to the server, to be sent to the specified user (case-insensitive,must be stood alone on a line)
```
!xxx
```
• Help menu(case-insensitive,must be stood alone on a line)
```
?
```
### *Class structure*
    
- Client: You need to run the main for starting the class. Two threads, for reading the
command and sending the command, will start.
- InputHandler: for pairing the command and transferring them into related protocol
- OutputHandler: for translating the stream into message, which will be presented to user
- ReaderThread: The thread class for receiving  and printing server's message
- WriterThread: The thread class for receiving user's command and sending to the server

### Chatroom Protocol
The application data for the chatroom consists of a sequence of data.
The format depends on
what kind of message is being sent.
All frames of application data begin with a message
identifier.

### *Class structure*
- Protocol Interface: message state of protocol
- ProtocalImp: implement of State



## How to run

1.Go to the server class, run server's main function to run the server on `localhost` with port `10000`

2.Go to the client class. In the client's main function, server's IP address (or localhost) and port `10000` are required to be passed in IntelliJ interface.

## Example of the client side output
<img width="592" alt="image" src="https://media.github.ccs.neu.edu/user/10167/files/f929d007-a40c-4e75-b229-549a4d234565">


## Assumptions
In the client:
1. if there are multiple commands (for @user,!user and @all), only the command at the
   top of the input will be use.
2. if the user's input does not match any command, the program will treat it as "@all", such as "who are you", "! hello", " @ home" and so on
3. if user input empty string, the client interface will ask for re-input again
4. @all including sending message to the client itself.
5. if CONNECT_RESPONSE returns false, the client won't be able to log off. Only it return true, then
    the program can be terminated.
6. Client will try to reconnect server when server closed during the phase of login. After login,
if the server closed, only error message will be thrown and reconnection will not happen
7. Server can connect up to 10 clients

## About Jar file
The project 4 was contained as a jar file for generating insult message. The file is listed as 
`assignment6/assignment4-1.0-SNAPSHOT.jar`
