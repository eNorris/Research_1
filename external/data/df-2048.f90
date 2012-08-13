!  Nuclear Engineering 204 - Radiation Measurement
!
!  Date:     14 April 2005
!
!
!  This is a data filter program to take the output from Genie2000
!  radiation measurement output and generate a file that can be read by Excel.
!
!  This version only uses the Channel Data Report and ignores the
!  other data.  The other reports, such as Energy Calibration will
!  need to be dealt with manually at this time.
!
 PROGRAM datafltr
 IMPLICIT NONE
 
 CHARACTER :: again,check_data
 CHARACTER (12) :: filename
 CHARACTER (14) :: junk
 INTEGER :: num_data
 INTEGER :: o_error,r_error,counter,channel,h,i,n
 INTEGER, DIMENSION(1,8) :: hold_table
 INTEGER, DIMENSION(2048) :: channel_table
 
 counter = 0
 channel = 0
 o_error = 0
 r_error = 0
 
 PRINT *
 PRINT *,"*******************************************************"
 PRINT *,"*************** DATA FILTER PROGRAM *******************"
 PRINT *,"*******************************************************"
 PRINT *
 PRINT *,"   !!! NOTE, before proceeding, this program is limited"
 PRINT *,"       to reading in files that are exactly 8 characters"
 PRINT *,"       long, plus a file extension.  An example of a "
 PRINT *,"       valid name:  Eucounts.txt"
 PRINT *,"       Please rename your file if you have to..."

 PRINT *
 
 DO i=1,50 
!  Read in the file we want to process
    PRINT *,"Please enter the file name, including document extension (.txt)"
    PRINT *,"and the file name MUST be EXACTLY 8 characters:"
    READ *,filename
    PRINT *
 
!  Open the file and check to make sure the file name is correct.  If it is,
!  read 8 more times to get past the header information.

    OPEN (unit=11,file=filename,status='old',action='read',position='rewind',iostat=o_error)
 
    IF (o_error /= 0) THEN
       PRINT *,"File <",filename,"> can not be opened!"
    ELSE
       DO h=1,8
          READ (11,*,iostat=r_error) junk
          counter = counter + 1
       END DO
       EXIT
    END IF
 END DO

!  Load the table with the appropriate values.

 DO
    READ (11,'(5x,a1)',iostat=r_error) check_data    

    IF (r_error<0) THEN
       CLOSE (11) 
       EXIT
    ELSE IF (check_data==":") THEN
       BACKSPACE 11
       READ (11,'(7x,8I8)',iostat=r_error) hold_table(1,:)    

       IF (r_error<0) EXIT

       DO i=1,8
          channel = channel + 1
          channel_table(channel) = hold_table(1,i)
       END DO
    ELSE
!
    END IF
 END DO

!  Write tabled data to output file

 PRINT *,"Total Channels Read:",channel

 OPEN (unit=22,file='chandata.txt',status='REPLACE',action='write',position='rewind',iostat=o_error)
 
 IF (o_error /= 0) THEN
    PRINT *,"Output File chandata.txt can not be opened!"
    PRINT *,"Error Number:",o_error
 END IF

 DO i = 1,channel
    WRITE (22,*) channel_table(i)
 END DO 

 PRINT *,"You will find the output data in CHANDATA.TXT"

 CLOSE (11)
 CLOSE (22)

 END PROGRAM
