
package reseau.usekryonet.tests;

import java.io.IOException;

import reseau.usekryonet.ServerProgram;

public class UseServerKryo
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		try
			{
			new ServerProgram(54444);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
