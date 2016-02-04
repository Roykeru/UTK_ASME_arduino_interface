
#include "message.h"
#include <Servo.h>

#define NUM_MOTORS 6

Servo mainFlippers;
Servo auxFlippers;
Servo rightFrontMotor;
Servo leftFrontMotor;
Servo servoMotor;
Servo combineMotor;

const int ledpin = 13;
const int timeout = 15000;
int lasttime = 0;

struct motor_t {
	byte value;
	byte pin;
} motors[NUM_MOTORS];

struct motor_message_t {
	byte motor_number;
	byte value;
};


//byte process_kill_message(
byte process_motor_message(struct motor_message_t*, byte);
void process_message(struct message_t *);

void process_message(struct message_t *message) {
	byte body_length;

	if (message->state != MESSAGE_READY) {
		return;
	}

	//Serial.print(message->data.header.length);
	//Serial.print(": ");

	body_length = message->data.header.length - sizeof(message->data.header);

	//Serial.print(message->data.header.action);
	//Serial.print(" ");

	switch (message->data.header.action) {
		case 'm':
			if (process_motor_message((struct motor_message_t *)&message->data.body, body_length)) {
				message_processed(message);
			}
			break;
			
		case 'k':
			leftFrontMotor.writeMicroseconds(1500);
			rightFrontMotor.writeMicroseconds(1500);
			mainFlippers.writeMicroseconds(1500);
			auxFlippers.writeMicroseconds(1500);
			combineMotor.writeMicroseconds(1500);
			servoMotor.writeMicroseconds(1500);
			break;
			
		default:
			Serial.println("ping");
			message_processed(message);
			break;
	}
}

//byte process_kill_message(){
	
//}

byte process_motor_message(struct motor_message_t *motor_message, byte size) {
	byte motor_number = motor_message->motor_number;

	//Serial.print(motor_number);
	//Serial.print(" ");
	
	if (!(0 <= motor_number && motor_number < NUM_MOTORS)) {
		return 1;
	}
	motors[motor_number].value = motor_message->value;

	//Serial.println(1000 + (int)motors[motor_number].value * 5);

	switch (motor_number){
		case 0:
			leftFrontMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 1:
			rightFrontMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 2:
			mainFlippers.writeMicroseconds(1000 + (int)motors[motor_number].value * 5); //30 & 147
			break;
			
		case 3:
			auxFlippers.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 4:
			combineMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 5:
			servoMotor.write(1000 + (int)motors[motor_number].value * 5);
			break;
				 
	}

	return 1;
}

struct message_t message;
void setup(void) {
  	Serial.begin(115200);
	  
	mainFlippers.attach(3); //main flippers
	auxFlippers.attach(5); //auxiliary flippers
	combineMotor.attach(6); // combine
	rightFrontMotor.attach(9); //Right Drive Motors
	leftFrontMotor.attach(10); //left Drive Motors
	servoMotor.attach(11); //Servo

}

void loop(void){ 
	
	if (read_message(&message)) {
		process_message(&message);
		//Serial.println("looping");
		lasttime = millis();
	}
	
	/*if (millis() - timeout < lasttime){
		leftFrontMotor.writeMicroseconds(1500);
		rightFrontMotor.writeMicroseconds(1500);
		mainFlippers.writeMicroseconds(1500);
		auxFlippers.writeMicroseconds(1500);
		combineMotor.writeMicroseconds(1500);
		servoMotor.writeMicroseconds(1500);
		Serial.println("Lost Signal");
		lasttime = millis();
	}*/
	
}
