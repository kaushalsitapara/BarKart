#include "HX711.h"
#include <MySQL_Connection.h>
#include <MySQL_Cursor.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
const int DOUT = D2;
const int CLK = D3;
HX711 scale;
char ssid[] = "JioFi";                 // Network Name
char pass[] = "hihellow1";                 // Network Password
byte mac[6];
//WiFiServer server(80);
//IPAddress ip(192, 168, 2, 2);
//IPAddress gateway(192, 168, 2, 1);
//IPAddress subnet(255, 255, 255, 0);
float calibration_factor = -756; // this calibration factor is adjusted according to my load cell
float units;
float ounces;
WiFiClient client;
MySQL_Connection conn((Client *)&client);
int val=0;
char INSERT_SQL[] = "update test.bill set scwt=%i where val=%i";
//char INSERT_SQL[] = "INSERT INTO officeto_plants.TBL_READINGS(ID_PLANT, AIR_HUMIDITY, AIR_TEMPERATURE, SOIL_MOISTURE_1, SOIL_MOISTURE_2) VALUES (1, NULL, NULL, %d, %d)";
char query[128];

IPAddress server_addr(192, 168,43,24);          // MySQL server IP
char user[] = "test";           // MySQL user
char password[] = "test";       // MySQL password
int temp =0;
void setup() {
  Serial.begin(115200);
Serial.println("Initialising connection");
  Serial.print(F("Setting static ip to : "));
 // Serial.println(ip);

  Serial.println("");
  Serial.println("");
  Serial.print("Connecting to ");
  Serial.println(ssid);
 // WiFi.config(ip, gateway, subnet); 
  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED) {
    delay(200);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi Connected");

  WiFi.macAddress(mac);
  Serial.print("MAC: ");
  Serial.print(mac[5],HEX);
  Serial.print(":");
  Serial.print(mac[4],HEX);
  Serial.print(":");
  Serial.print(mac[3],HEX);
  Serial.print(":");
  Serial.print(mac[2],HEX);
  Serial.print(":");
  Serial.print(mac[1],HEX);
  Serial.print(":");
  Serial.println(mac[0],HEX);
  Serial.println("");
  Serial.print("Assigned IP: ");
  Serial.print(WiFi.localIP());
  Serial.println("");

  Serial.println("Connecting to database");

  while (conn.connect(server_addr, 3306, user, password) != true) {
    delay(200);
    Serial.print ( "." );
  }

  Serial.println("");
  Serial.println("Connected to SQL Server!");
scale.begin(DOUT, CLK);
  scale.set_scale();
  
  scale.tare();  //Reset the scale to 0

  long zero_factor = scale.read_average(); //Get a baseline reading

  Serial.print("Zero factor: "); //This can be used to remove the need to tare the scale. Useful in permanent scale projects.
  Serial.println(zero_factor);
}

void loop() {

  scale.set_scale(calibration_factor); //Adjust to this calibration factor

  
  units = scale.get_units(), 10;
  if(units>=(temp+10)){
    if (units < 0)
  {
    units = 0.00;
  }
 temp=temp+units;
  Serial.print(units);
  Serial.print(" grams"); 
  Serial.print(" calibration_factor: ");
  Serial.print(calibration_factor);
  Serial.println();
sprintf(query, INSERT_SQL, units,val);
  //sprintf(query, INSERT_SQL, soil_hum, t);

  Serial.println("Recording data.");
  Serial.println(query);
  
  MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
  
  cur_mem->execute(query);

  delete cur_mem;}
  else{
  Serial.println("no");
  }
}
