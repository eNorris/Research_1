package com.research.Utilities;

public class McaProtocol {
	// Status Data Structure (20 8 bit bytes)
	public class StatusDataStructure{
		byte dataChkSum_3;
		byte dataChkSum_2;
		byte dataChkSum_1;
		byte dataChkSum_0;
		byte presetTime_2;
		byte presetTime_1;
		byte presetTime_0;
		byte battery;
		byte realTime_2;
		byte realTime_1;
		byte realTime_0;
		byte realTime_75;
		byte liveTime_2;
		byte liveTime_1;
		byte liveTime_0;
		byte liveTime_75;
		byte threshold_1;
		byte threshold_0;
		byte flags;
		byte checkSum;
	}
	
	public class StartStampStructure{
		byte century;	// 19 or 20
		byte year;		// 0-99
		byte month;		// 1-12
		byte day;		// 1-31
		byte notUsed;	// 
		byte hours;		// 0-23
		byte minutes;	// 0-59
		byte seconds;	// 0-59
		
		public StartStampStructure startStampNow(){
			StartStampStructure now = new StartStampStructure();
			// TODO - finish
			return now;
		}
	}
	
	public class McaCommand{
		public static final byte SEND_DATA_AND_CHECK_SUM_COMMAND = 0;
		public static final byte SEND_DATA_GROUP_AND_SN_COMMAND = 16;
		public static final byte GET_START_STAMP_COMMAND = 48;
		public static final byte SET_START_DATE = 20; // 21st century (19 = 20th century)
		public static final byte SET_START_TIME = 37;
		public static final byte SET_GROUP_COMMAND = 17;
		public static final byte SET_MCA_LOCK_COMMAND = 117;
		public static final byte CONTROL_COMMAND = 1;
		public static final byte PRESET_TIME_COMMAND = 2;
		
		public byte commandCode;	// byte 0
		public byte data0;			// byte 1
		public byte data1;			// byte 2
		public byte data2;			// byte 3
		public byte checkSum;		// byte 4
		
		public void sendDataAndCheckSumCommand(){
			// byte 0 = 0
			// byte 1 = lower byte of relative address of first byte to be transmitted from MCA
			// byte 2 = upper byte of relative address of first byte to be transmitted from MCA
			// byte 3 = 0
			// byte 4 = checksum
			// byte 1,2 is calculated as channelNo * 4 + n where is is 0 for transfer of lower word of 
			// channel data and n is 2 for transfer of upper word
			// Checksum is sum of all bytes of channel data transmittee mod 2^16
		}
		
		public void sendDataGroupAndSnCommand(){
			// byte 0 = 16
			// byte 1 = same as sendDataAndCheckSumCommand()
			// byte 2 = ""
			// byte 3 = Don't care : NOT ZERO (included in check sum)
			// byte 4 = checksum
			
			// returns a status where dataChkSum_0 = group number and dataChkSum_3 and dataChkSum_3 form the serial
			// number of the MCA connected to the computer
		}
		
		public void getStartStampCommand(){
			// byte 0 = 48
			// byte 1 = Don't care : NOT ZERO (included in checksum)
			// byte 2 = Don't care : NOT ZERO (included in checksum)
			// byte 3 = Don't care : NOT ZERO (included in checksum)
			// byte 4 = checksum
			
			// MCA sends start stamp structure
		}
		
		public void  setStartDateCommand(){
			// byte 0 = 20
			// byte 1 = year in bcd
			// byte 2 = month in bcd
			// byte 3 = day in bcd
			// byte 4 = checksum
		}
		
		public void setStartTimeCommand(){
			// byte 0 = 37
			// byte 1 = hour in bcd
			// byte 2 = mintue in bcd
			// byte 3 = second in bcd
			// byte 4 = checksum
		}
		
		public void setGroupCommand(){
			// byte 0 = 17
			// byte 1 = 0
			// byte 2 = group number
			// byte 3 = don't care : NOT ZERO, included in chekcsum
			// byte 4 = checksum
			// MCA must not be currently acquiring data or this will be ignored
			
			// possible values for group number depends on resolution:
			// 16K:	0-1
			// 8K:	0-3
			// 4K:	0-7
			// 2K:	0-15
			// 1K:	0-31
			// 512:	0-63
			// 256:	0-127
		}
		
		public void setMcaLockCommand(){
			// byte 0 = 117
			// byte 1 = lock number
			// byte 2 = lock number
			// byte 3 = don't care: NOT ZERO, added in checksum
			// byte 4 = checksum
		}
		
		public void controlCommand(){
			// byte 0 = 1
			// byte 1 = control flags (flag byte)
			// byte 2 = lower byte of threshold
			// byte 3 = upper byte of threshold
			// byte 4 = checksum
			
			// should be proceeded by sendData() to get the current values of flags and threshold
		}
		
		public void presetTimeCommand(){
			// byte 0 = 2
			// byte 1 = first (lower) byte of preset time
			// byte 2 = second byte of preset time
			// byte 3 = third byte of preset time
			// byte 4 = checksum
			
			// does NOT affect the timer type (live/real)
		}
		
		public void deleteCommand(){
			// byte 0 = 5
			// byte 1 = 1 to delete data, 0 otherwise
			// byte 2 = 1 to delete time, 0 otherwise
			// byte 3 = don't care: NOT ZERO, added in checksum
			// byte 4 = checksum
		}
		
	}
	
	// FIXME - Move the below into the StatusDataStructure class and then don't send an arg
	
	public static int getDataChkSum(StatusDataStructure data){
		// DataChkSum is either sum mod 2^16 of all bytesw of channel data transmitted at last data exchange or 
		// current group numberand serial no of MCA
		return data.dataChkSum_3 << 24 + data.dataChkSum_2 << 16 + data.dataChkSum_1 << 8 + data.dataChkSum_0;
	}
	
	public static int getPresetTime(StatusDataStructure data){
		// 24 bit int that is the preset acquisition time of the MCA in seconds, can be modified via the 
		// PRESET_TIME_COMMAND
		return data.presetTime_2 << 16 + data.presetTime_1 << 8 + data.presetTime_0;
	}
	
	public static boolean batteryExternalPower(StatusDataStructure data){
		// Battery = 0 denotes external power connected
		return data.battery == 0;
	}
	
	public static int getBatteryCapacityPercent(StatusDataStructure data){
		// TODO - how determine if Alkaline or NiCd battery?
		boolean isAlkaline = true;
		boolean isNiCd = false;
		if(isAlkaline){
			return 0;
		}else if(isNiCd){
			return 0;
		}else{
			return 0;
		}
	}
	
	public static double getRealTime(StatusDataStructure data){
		return (double)(data.realTime_2 << 16 + data.realTime_1 << 8 + data.realTime_0) + (double)(data.realTime_75) / 75.0;
	}
	
	public static int getThreshold(StatusDataStructure data){
		// returns channel no of lower threshold
		return data.threshold_1 << 8 + data.threshold_0;
	}
	
	public static int getNoChannels(StatusDataStructure data){
		byte lower3Bits = (byte) (data.flags & 0x00000111);
		if((lower3Bits ^ 0x00000000) == 0x000000000)
			return 16384; //2^14
		if((lower3Bits ^ 0x00000001) == 0)
			return 8192; // 2^13
		if((lower3Bits ^ 0x00000010) == 0)
			return 4096; // 2^12
		if((lower3Bits ^ 0x00000011) == 0)
			return 2048; // 2^11
		if((lower3Bits ^ 0x00000100) == 0)
			return 1024; // 2^10
		if((lower3Bits ^ 0x00000101) == 0)
			return 512; // 2^9
		if((lower3Bits ^ 0x00000110) == 0)
			return 256; // 2^8
		return 0;
	}
	
	public static boolean isLiveTime(StatusDataStructure data){
		// bit 3: 1 = live, 0 = real
		return (data.flags & 0x0001000) != 0;
	}
	
	public static boolean isRealTime(StatusDataStructure data){
		return !isLiveTime(data);
	}
	
	public static boolean isStopFlagSet(StatusDataStructure data){
		return (data.flags & 0x00010000) != 0;
	}
	
	public static boolean isStartFlagSet(StatusDataStructure data){
		return !isStopFlagSet(data);
	}
	
	public static boolean isMcaProtected(StatusDataStructure data){
		return (data.flags & 0x00100000) != 0;
	}
	
	public static boolean isBatteryAlkaline(StatusDataStructure data){
		// 1 = NiCd, 0 = Alkaline
		return (data.flags & 0x01000000) == 0;
	}
	
	public static boolean isBatteryNiCd(StatusDataStructure data){
		return !isBatteryAlkaline(data);
	}
	
	public static boolean isBackupBatteryGood(StatusDataStructure data){
		return (data.flags & 0x10000000) == 0;
	}
	
	public boolean isValidStatus(StatusDataStructure data){
		int sum = 0;
		sum += (data.dataChkSum_3 + data.dataChkSum_2 + data.dataChkSum_1 + data.dataChkSum_0);
		sum += (data.presetTime_2 + data.presetTime_1 + data.presetTime_0);
		sum += data.battery;
		sum += (data.realTime_2 + data.realTime_1 + data.realTime_0 + data.realTime_75);
		sum += (data.liveTime_2 + data.liveTime_1 + data.liveTime_0 + data.liveTime_75);
		sum += (data.threshold_1 + data.threshold_0);
		sum += data.flags;
		return (sum % 256) == data.checkSum;
	}
	
}
































