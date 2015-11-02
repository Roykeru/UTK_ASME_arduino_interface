
#include "message.h"
#include <Servo.h>

#define NUM_MOTORS 6

Servo mainFlippers;
Servo auxFlippers;
Servo rightBackMotor;
Servo rightFrontMotor;
Servo leftBackMotor;
Servo leftFrontMotor;
Servo servoMotor;
Servo combineMotor;

const int ledpin = 13;

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
			leftBackMotor.writeMicroseconds(1500);
			rightFrontMotor.writeMicroseconds(1500);
			rightBackMotor.writeMicroseconds(1500);
			mainFlippers.writeMicroseconds(1500);
			auxFlippers.writeMicroseconds(1500);
			break;
		default:
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
			//leftFrontMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 1:
			leftBackMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 2:
			rightFrontMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 3:
			//rightBackMotor.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 4:
			mainFlippers.writeMicroseconds(1000 + (int)motors[motor_number].value * 5); //30 & 147
			break;
			
		case 5:
			auxFlippers.writeMicroseconds(1000 + (int)motors[motor_number].value * 5);
			break;
			
		case 6:
			servoMotor.write((int)motors[motor_number].value * .9);
			break;
			 
		case 7:
			combineMotor.writeMicroseconds(1000 + (int)motors[motor_number].value*.5);
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
	leftBackMotor.attach(10); //left Drive Motors
	servoMotor.attach(11); //Servo
	//rightBackMotor.attach(6);
	//leftFrontMotor.attach(11);
	//pinMode(ledpin, OUTPUT);
}

void loop(void) {
	if (read_message(&message)) {
		process_message(&message);
		//Serial.println("looping");
	}

}
