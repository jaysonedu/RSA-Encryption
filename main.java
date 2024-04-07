import java.math.*;
import java.util.*;
/**
 * Implementation of the RSA algorithm
 * @author (Jason Qin)
 * @version (5-17-2023)
 */

public class main
{
    public static void main(String[] args)
    {
    // choose two primes    
    int P = 53;
    int Q = 59;
    int N = P * Q;
    
    // totient for two primes is just product of one minus each of them
    int totient = (P - 1) * (Q - 1);
    
    // choose a small exponent e
    int e = 2;
    // public key is made of N and e
    
    // makes gcd of e and totient == 1, and e smaller than totient
    while (e < totient) 
    {
        if (gcd(e, totient) == 1)
        {
            break;
        }
        else
        {
            e++;
        }
    }
    
    // Private Key
    int k = 2;
    int Private = ((k * totient) + 1) / e;
    
    // Scanner to read input
    Scanner sc = new Scanner(System.in);
    System.out.println("enter e or d to encrypt or decrypt");
    String s = sc.nextLine();
    
    // encryption
    if (s.equals("e"))
    {
        System.out.println("You have chosen to encrypt.");
        String output = encrypt(e, N);
        System.out.println("The encrypted message is: " + output);
    }
    
    // decryption
    else if (s.equals("d"))
    {
        System.out.println("You have chosen to decrypt.");
        String output = decrypt(Private, N);
        System.out.println("The decrypted message is: " + output);
    }
    else
    {
        System.out.println("Please input only e or d");
    }
    }
    
    // encryption
    public static String encrypt(int e, int N)
    {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the original message: ");
        String msg = sc1.nextLine();
        System.out.println("How many buffer characters should be added? ");
        int bufferIn = sc1.nextInt();
        
        // adds random characters to beginning and end of string
        msg = randomBuffer(msg, bufferIn);
        
        // converts input string to BigInteger array
        BigInteger[] arr = convert1(msg);
        String out = "";
        
        // for every element in arr, performs encryption and appends to String
        for (int i = 0; i<arr.length; i++)
        {
            BigInteger temp = arr[i].pow(e);
            temp = temp.mod(BigInteger.valueOf(N));
            out = out + temp + " ";
        }
        return out;
    }
    
    // decryption
    public static String decrypt(int Private, int N)
    {
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Enter the encrypted message: ");
        String msg = sc2.nextLine();
        System.out.println("Enter the buffer number: ");
        int bufferOut = sc2.nextInt();
        
        // splitting string to string array
        String[] temp2 = msg.split(" ", 0);
        BigInteger[] arr = new BigInteger[temp2.length];
        BigInteger[] arr1 = new BigInteger[temp2.length];
        // converting string array to BigInteger array
        for (int i = 0; i < temp2.length; i++)
        {
            arr[i] = new BigInteger(temp2[i]);
        }
        
        // runs decryption algorithm 
        for (int i = 0; i<arr.length; i++)
        {
            BigInteger temp = arr[i].pow(Private);
            temp = temp.mod(BigInteger.valueOf(N));
            arr1[i] = temp;
        }
        
        // converts BigInteger array to char array
        String output = convert2(arr1);
        
        // removes the encryption buffer
        output = removeBuffer(output, bufferOut);
        return output;
    }
    
    // function to return gcd of two numbers with Euclidean algorithm.
    public static int gcd(int a, int b)
    {
        while (true)
        {
            if (b == 0)
            { 
                return a;
            }
            else
            {
                return gcd(b, Math.abs(a - b));
            }
    }
    }
    
    // function to convert string to ascii array
    public static BigInteger[] convert1(String s)
    {
        BigInteger[] chars = new BigInteger[s.length()];
        
        // creates array with every element of string to character array
        char[] ch = s.toCharArray();
        for (int i = 0; i < s.length(); i++)
        {
            chars[i] = BigInteger.valueOf((int) ch[i]);
        }
        return chars;
    }
    
    // function to convert ascii to string array
    public static String convert2(BigInteger[] chars)
    {
        String output = "";
        for (int i = 0; i < chars.length; i++)
        {
            output = output + Character.toString((char) chars[i].intValue());
        }
        return output;
    }
    
    // generates a random buffer of characters of pre-determined length
    public static String randomBuffer(String s, int input)
    {
        Random rnd = new Random();
        for (int i = 0; i<input; i++)
        {
            s = Character.toString((char)(32 + rnd.nextInt(95))) + s + Character.toString((char)(32 + rnd.nextInt(95)));
        }
        return s;
    }
    
    // removes buffer characters at beginning and end
    public static String removeBuffer(String s, int input)
    {
        s = s.substring(input, s.length()-input);
        return s;
    }
}