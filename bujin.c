#include <reg51.h>
#include <intrins.h>
#defint PP P0
typedef unsigned char uchar;
uchar xdata  cow _at_ 0x8002;
uchar xdata  row _at_ 0x8001;
uchar xdata  led _at_ 0x8004;
uchar xdata  CL _at_ 0x9000;   // 点阵1
uchar xdata  CH _at_ 0x9001;   // 
uchar xdata  RL _at_ 0x9002;   // 
uchar xdata  RH _at_ 0x9003;   // 
uchar direction = 0;  //0为正转,1为反转
uchar onoff = 0; //关为0,开为1
uchar index = 0; //通过index指示电机转动,八拍
uchar keyval = 0x00;
uchar key; 
uchar num = 0;
int o;
uchar code Table_C[]={0x7f,0xbf,0xdf,0xef,0xf7,0xfb,0xfd,0xfe};
uchar code table[ ]={0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,0x7f,0x6f};    //数字0~9的段码 
unsigned char code beatCode[ ] = {0x09,0x01,0x03,0x02,0x06,0x04,0x0c,0x08};
unsigned char code beatCode_r[ ] = {0x08,0x0c,0x04,0x06,0x02,0x03,0x01,0x09};
uchar code Table_B[][100]=
{
{0x00,0x00,0x3C,0x42,0x42,0x3C,0x00,0x00},/*"0"*/

{0x00,0x00,0x00,0x24,0x3E,0x20,0x00,0x00},/*"1"*/

{0x00,0x00,0x3A,0x2A,0x2A,0x2E,0x00,0x00},/*"2"*/

{0x00,0x00,0x2A,0x2A,0x2A,0x3E,0x00,0x00},/*"3*/

{0x00,0x00,0x18,0x14,0x3E,0x10,0x00,0x00},/*"4"*/

{0x00,0x00,0x2E,0x2A,0x2A,0x3A,0x00,0x00},/*"5"*/

{0x00,0x00,0x3E,0x2A,0x2A,0x3A,0x00,0x00},/*"6"*/

{0x00,0x00,0x00,0x02,0x02,0x3E,0x00,0x00},/*"7"*/

{0x00,0x00,0x3E,0x2A,0x2A,0x3E,0x00,0x00},/*"8"*/

{0x00,0x00,0x2E,0x2A,0x2A,0x3E,0x00,0x00},/*"9"*/

};
 void delay10ms(void)//软件延时子程序   
{
   uchar i,j;
for(i=0;i<2;i++)
for(j=0;j<124;j++)
           ;
 }
void TableA(uchar k)
{
	uchar q;
	for(q=0;q<8;q++)
	{
		RL=~Table_C[q];
		CL=~Table_B[k][q];
		delay10ms();
	}
}
void TableB(uchar k)
{
	uchar q;
	for(q=0;q<8;q++)
	{
		RH=~Table_C[q];
		CL=~Table_B[k][q];
		delay10ms();
	}
}
void TableC(uchar k)
{
	uchar q;
	for(q=0;q<8;q++)
	{
		RL=~Table_C[q];
		CH=~Table_B[k][q];
		delay10ms();
	}
}
void delay(unsigned int i)//延时
{
 while(--i);                
}
 void led_delay(void)//数码管动态扫描延时     
 {
   uchar j;
for(j=0;j<200;j++)
;
  }

void display(uchar k)//按键值的数码管显示子程序
{     
	
	if(direction == 0)
		led = 0x3f; 	//数码管上百位数的显示
	else
		led = 0x06;
	cow = 0x04;
		led_delay();
	led=0x00;
		if(onoff == 0)
		led = 0x3f; 	//数码管上千位数的显示
	else
		led = 0x06;
	cow = 0x08;
	led_delay();
	led=0x00;
	led = table[k];           //数码管上的个位显示
	cow = 0x01;
	led_delay(); 
	led=0x00;
 }
void Stop()
{
    onoff++;
    if (onoff > 1)
        onoff = 0;
}
void int1()
{
    direction++;
    if (direction > 1)
        direction = 0;
}
uchar keyScan(void)
{
	cow = 0x00;
	if (row != 0x0f)                  //判断键盘是否被改变
	{
		uchar key = 0x00;
		delay10ms();
		cow = 0x00;                        
		switch (row)
		{
		case 0x07:
			key = 0x40;                  
			break;
		case 0x0b:
			key = 0x30;
			break;
		case 0x0d:
			key = 0x20;
			break;
		case 0x0e:
			key = 0x10;
			break;
		default:
			return key;
		}
		cow = 0x1f; 
		if (row != 0x0f)
			key = key + 0x01;
		delay10ms();

		cow = 0x2f; 
		if (row != 0x0f)
			key = key + 0x02;
		delay10ms();

		cow = 0x37; 
		if (row != 0x0f)
			key = key + 0x03;
		delay10ms();

		cow = 0x3d;
		if (row != 0x0f)
			key = key + 0x05;
		delay10ms();

		cow = 0x3e; 
		if (row != 0x0f)
			key = key + 0x06;
		delay10ms();

    cow = 0x00;
		delay10ms();

    while (row != 0x0f)
		delay10ms();
		return key;
	}
	return 0x00;
}
uchar keyva(uchar keyval)
{
	 if(key==0x41)
		 return 7;
	 else if(key==0x42)
		 return 8;
	 else if(key==0x43)
		 return 9;
	 else if(key==0x31)
		 return 4;
	 else if(key==0x32)
		 return 5;
	 else if(key==0x33)
		 return 6;
	 else if(key==0x21)
		 return 1;
	 else if(key==0x22)
		 return 2;
	 else if(key==0x23)
		 return 3;
	 else if(key==0x11)
		 return 0;
	 else return keyval;
}
void initTime()
{
	 /* LED */
	EA = 1; 
	ET0=1;
	ET1=1;
	PT1 = 1;
  TMOD=0x11;            
  TH0=(65536-50000)/256;  //65536-500
  TL0=(65536-50000)%256; 
	TH1=(65536-50000)/256;  //65536-500
  TL1=(65536-50000)%256;	
  TR0=1;
	TR1=1;
}
void time0() interrupt 1
{	
	TH0=(65536-50000)/256;  //65536-500
  TL0=(65536-50000)%256;
	display(keyval);
}
void time1() interrupt 3
{
	TH1=(65536-50000)/256;  //65536-500
  TL1=(65536-50000)%256;	
	num++;
}
void main()
{
		while(1){
			key=keyScan();
			if(key==0x45){
				Stop();
				key = 0x00;
				initTime();
				break;
			}
		}
	while(1){
		TableA(onoff);
		TableB(direction);
		TableC(keyval);
		key=keyScan();
		if(key==0x46)
			int1();
		if(key==0x45)
			Stop();
		if(onoff==1){
		if(num >= keyval)
		{
			num=0;
			if(direction==0)
		{
			keyval=keyva(keyval);
			P1 = beatCode[index];		      //输出时序
			index++;			  
			index= index & 0x07; //准备输出下一节拍
			//delay(keyval*40);	//max speed standard:150 延迟越小(脉冲间隔越小）速度越快
    }		  
		else if(direction==1)
		{
			keyval=keyva(keyval);
			P1 = beatCode_r[index];
			index++;
			index= index & 0x07;
			//delay(keyval*40);//  	
   }
		}
	}
		
	
 }
}
