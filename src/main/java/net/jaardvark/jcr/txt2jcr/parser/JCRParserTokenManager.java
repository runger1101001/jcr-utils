/* Generated By:JavaCC: Do not edit this line. JCRParserTokenManager.java */
package net.jaardvark.jcr.txt2jcr.parser;
import java.io.Reader;
import net.jaardvark.jcr.txt2jcr.JCRParseEventHandler;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import net.jaardvark.jcr.txt2jcr.Tuple;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.jackrabbit.util.ISO8601;

/** Token Manager. */
public class JCRParserTokenManager implements JCRParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 44:
         return jjStopAtPos(0, 12);
      case 58:
         return jjStopAtPos(0, 13);
      case 123:
         return jjStopAtPos(0, 10);
      case 125:
         return jjStopAtPos(0, 11);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 92;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xfbff6bfaffffd9ffL & l) != 0L)
                  {
                     if (kind > 18)
                        kind = 18;
                     jjCheckNAddStates(0, 2);
                  }
                  else if ((0x2400L & l) != 0L)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 34)
                  {
                     if (kind > 1)
                        kind = 1;
                  }
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 14)
                        kind = 14;
                     jjCheckNAddStates(3, 5);
                  }
                  else if (curChar == 45)
                     jjCheckNAddTwoStates(61, 62);
                  else if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 1:
                  if ((0x2400L & l) != 0L && kind > 8)
                     kind = 8;
                  break;
               case 2:
                  if (curChar == 10 && kind > 8)
                     kind = 8;
                  break;
               case 3:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 37:
                  if ((0xfbff6bfaffffd9ffL & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAddStates(0, 2);
                  break;
               case 38:
                  if ((0xfbff6bfaffffd9ffL & l) != 0L)
                     jjCheckNAddTwoStates(38, 39);
                  break;
               case 39:
                  if (curChar == 58)
                     jjCheckNAdd(40);
                  break;
               case 40:
                  if ((0xfbff6bfaffffd9ffL & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(40);
                  break;
               case 60:
                  if (curChar == 45)
                     jjCheckNAddTwoStates(61, 62);
                  break;
               case 61:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 14)
                     kind = 14;
                  jjCheckNAdd(61);
                  break;
               case 62:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(62);
                  break;
               case 63:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 14)
                     kind = 14;
                  jjCheckNAddStates(3, 5);
                  break;
               case 64:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 65;
                  break;
               case 65:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 66;
                  break;
               case 66:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 67;
                  break;
               case 67:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 68;
                  break;
               case 68:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 69;
                  break;
               case 69:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 70;
                  break;
               case 70:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 71;
                  break;
               case 71:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 72;
                  break;
               case 72:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 73;
                  break;
               case 74:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 75;
                  break;
               case 75:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 76;
                  break;
               case 76:
                  if (curChar == 58)
                     jjstateSet[jjnewStateCnt++] = 77;
                  break;
               case 77:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 78;
                  break;
               case 78:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 79;
                  break;
               case 79:
                  if (curChar == 58)
                     jjstateSet[jjnewStateCnt++] = 80;
                  break;
               case 80:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 81:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 82;
                  break;
               case 82:
                  if (curChar == 46)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 83:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 84:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 85:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 86;
                  break;
               case 86:
                  if ((0x280000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 87;
                  break;
               case 87:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 88;
                  break;
               case 88:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 89;
                  break;
               case 89:
                  if (curChar == 58)
                     jjstateSet[jjnewStateCnt++] = 90;
                  break;
               case 90:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 91:
                  if ((0x3ff000000000000L & l) != 0L && kind > 17)
                     kind = 17;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xc7ffffffc7ffffffL & l) != 0L)
                  {
                     if (kind > 18)
                        kind = 18;
                     jjCheckNAddStates(0, 2);
                  }
                  if ((0x1000000010L & l) != 0L)
                     jjAddStates(6, 7);
                  else if ((0x400000004L & l) != 0L)
                     jjAddStates(8, 9);
                  else if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 35;
                  else if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  else if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 27;
                  else if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 19;
                  else if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 14;
                  else if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 10;
                  else if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 4:
                  if ((0x8000000080L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 5:
               case 16:
                  if ((0x400000004000L & l) != 0L)
                     jjCheckNAdd(4);
                  break;
               case 6:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
                  if ((0x2000000020L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 9:
                  if ((0x200000002000L & l) != 0L)
                     jjCheckNAdd(8);
                  break;
               case 10:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if ((0x10000000100L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 13:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 14:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 15:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 17:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 18:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 17;
                  break;
               case 19:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 20:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 21:
                  if ((0x800000008L & l) != 0L)
                     jjCheckNAdd(8);
                  break;
               case 22:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 23:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 24:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 25:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 26:
                  if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 27:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 26;
                  break;
               case 28:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 29:
                  if ((0x2000000020L & l) != 0L && kind > 16)
                     kind = 16;
                  break;
               case 30:
                  if ((0x20000000200000L & l) != 0L)
                     jjCheckNAdd(29);
                  break;
               case 31:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 32:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 33:
                  if ((0x8000000080000L & l) != 0L)
                     jjCheckNAdd(29);
                  break;
               case 34:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 35:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 36:
                  if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 37:
                  if ((0xc7ffffffc7ffffffL & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAddStates(0, 2);
                  break;
               case 38:
                  if ((0xc7ffffffc7ffffffL & l) != 0L)
                     jjCheckNAddTwoStates(38, 39);
                  break;
               case 40:
                  if ((0xc7ffffffc7ffffffL & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(40);
                  break;
               case 41:
                  if ((0x400000004L & l) != 0L)
                     jjAddStates(8, 9);
                  break;
               case 42:
                  if ((0x200000002000000L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 43:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 44:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 45:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 44;
                  break;
               case 46:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 45;
                  break;
               case 47:
                  if ((0x400000004000L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 48:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 49:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 50:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 49;
                  break;
               case 51:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               case 52:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 51;
                  break;
               case 53:
                  if ((0x1000000010L & l) != 0L)
                     jjAddStates(6, 7);
                  break;
               case 54:
                  if ((0x100000001000L & l) != 0L)
                     jjCheckNAdd(8);
                  break;
               case 55:
                  if ((0x400000004L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 54;
                  break;
               case 56:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 55;
                  break;
               case 57:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 56;
                  break;
               case 58:
                  if ((0x10000000100000L & l) != 0L)
                     jjCheckNAdd(8);
                  break;
               case 59:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 58;
                  break;
               case 73:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 74;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAddStates(0, 2);
                  break;
               case 38:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(38, 39);
                  break;
               case 40:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(40);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 92 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_1()
{
   return jjMoveNfa_1(0, 0);
}
private int jjMoveNfa_1(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 3;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xffffffffffffdbffL & l) != 0L)
                  {
                     if (kind > 4)
                        kind = 4;
                  }
                  if (curChar == 34)
                  {
                     if (kind > 3)
                        kind = 3;
                  }
                  break;
               case 1:
                  if (curChar == 34 && kind > 3)
                     kind = 3;
                  break;
               case 2:
                  if ((0xffffffffffffdbffL & l) != 0L && kind > 4)
                     kind = 4;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (kind > 4)
                     kind = 4;
                  if (curChar == 92)
                  {
                     if (kind > 2)
                        kind = 2;
                  }
                  break;
               case 2:
                  if (kind > 4)
                     kind = 4;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((jjbitVec0[i2] & l2) != 0L && kind > 4)
                     kind = 4;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_2()
{
   return jjMoveNfa_2(0, 0);
}
private int jjMoveNfa_2(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 1;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     kind = 5;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  kind = 5;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((jjbitVec0[i2] & l2) != 0L && kind > 5)
                     kind = 5;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 1 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_3(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_3(int pos, long active0)
{
   return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_3()
{
   switch(curChar)
   {
      case 42:
         return jjStopAtPos(0, 21);
      default :
         return jjMoveNfa_3(0, 0);
   }
}
private int jjMoveNfa_3(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 4;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xfbff6bfaffffd9ffL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(10, 12);
                  break;
               case 1:
                  if ((0xfbff6bfaffffd9ffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if (curChar == 58)
                     jjCheckNAdd(3);
                  break;
               case 3:
                  if ((0xfbff6bfaffffd9ffL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(3);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xc7ffffffc7ffffffL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(10, 12);
                  break;
               case 1:
                  if ((0xc7ffffffc7ffffffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 3:
                  if ((0xc7ffffffc7ffffffL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(3);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(10, 12);
                  break;
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 3:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(3);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 4 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   38, 39, 40, 61, 62, 64, 57, 59, 46, 52, 1, 2, 3, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, "\173", "\175", 
"\54", "\72", null, null, null, null, null, null, null, "\52", null, null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "insideString",
   "afterQuote",
   "insideProp",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, 1, 2, 0, -1, 1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x37fe09L, 
};
static final long[] jjtoSkip = {
   0xc001c0L, 
};
static final long[] jjtoMore = {
   0x36L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[92];
private final int[] jjstateSet = new int[184];
protected char curChar;
/** Constructor. */
public JCRParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public JCRParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 92; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 4 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100000200L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         break;
       case 2:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_2();
         break;
       case 3:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100000200L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_3();
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
