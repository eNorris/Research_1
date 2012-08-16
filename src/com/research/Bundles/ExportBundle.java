package com.research.Bundles;

public class ExportBundle{
	public static enum ExportTypes{
		NONE, XML, TXT, PROJECT
	}
	
	public static enum NewLineCode{
		WINDOWS, UNIX, ACORN, COMMODORE
	}
	
	public static enum FileExtension{
		SDATA, CSV
	}
	
	public String userInput = "spectrumData";
	public String outFile = "spectrumData.sdata";
	public ExportTypes exportType = ExportTypes.NONE;
	public NewLineCode newLineCode = NewLineCode.WINDOWS;
	public FileExtension fileExtension = FileExtension.SDATA;
}