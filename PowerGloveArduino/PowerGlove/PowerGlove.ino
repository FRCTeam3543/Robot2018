// flex sensor constants
const int FLEX_PIN=A0;
const float VCC = 4.98;
const float R_DIV = 40000.0;
const float STRAIGHT_RESISTANCE = 4000.0;
const float BEND_RESISTANCE = 8000.0;
const int BEND_CLOSE_MIN = 15;
const int CLAW_CLOSED = 1;
const int CLAW_OPEN = 0;
const int WRIST_UP = 4;
const int WRIST_DOWN = 2;
const int WRIST_NEUTRAL = 0;

const int WRIST_DOWN_VAL = -8000;
const int WRIST_UP_VAL = 10000;

//gyro constants
#include<Wire.h>
const int MPU6050_addr=0x68;
int16_t AccX,AccY,AccZ,Temp,GyroX,GyroY,GyroZ;


void setup ()
{
  Serial.begin (9600);
  pinMode(FLEX_PIN, INPUT);

  Wire.begin();
  Wire.beginTransmission(MPU6050_addr);
  Wire.write(0x6B);
  Wire.write(0);
  Wire.endTransmission(true);
}


void loop ()
{
  int flexADC = analogRead (FLEX_PIN);
  float flexV = flexADC * VCC / 1023.0;
  float flexR = R_DIV * (VCC / flexV - 1.0);
//  Serial.print("Resistance: " + String(flexR) + "ohms");
  float angle = map(flexR, STRAIGHT_RESISTANCE, BEND_RESISTANCE, 0, 90.0);
//  Serial.print (" || Bend: " + String(angle) + "degrees");
//  Serial.println();
//  delay(100);
  
  Wire.beginTransmission(MPU6050_addr);
  Wire.write(0x3B);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU6050_addr,14,true);
  AccY= Wire.read()<<8|Wire.read();
  GyroY = Wire.read()<<8|Wire.read();
//  Serial.print(" || AccY = "); Serial.print(AccY);
//  Serial.print(" || GyroY = "); Serial.print(GyroY);
//  Serial.println();
  delay(100);
  int out = WRIST_NEUTRAL | CLAW_OPEN;
  // wrist
  if (GyroY >= WRIST_UP_VAL) out |= WRIST_UP;
  else if (GyroY <= WRIST_DOWN_VAL) out |= WRIST_DOWN;
  // claw
  if (angle >= BEND_CLOSE_MIN) {
    out |= CLAW_CLOSED;
  }
  Serial.print(out);


  
}


