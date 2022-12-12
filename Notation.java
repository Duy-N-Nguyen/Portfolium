/**
 * 
 * @author Duy Nguyen
 *
 */
public class Notation
{
	/**
	 * Converts postfix to infix
	 * @param postfix string format to infix
	 * @return infix expression
	 * @throws InvalidNotationFormatException throws if string postfix is invalid format
	 */
	public static String convertPostfixToInfix(String postfix) throws InvalidNotationFormatException
	{
		NotationStack <String> nStack = new NotationStack <String>();
		//String finalAnswer = "";
		String answerString = "";
		String moveLeft;
		String moveRight;
		String leftPara = "(";
		String rightPara = ")";	
		try 
		{
			for (int i = 0; i < postfix.length(); i++)
			{
				if (postfix.charAt(i) >= 48 && postfix.charAt(i) <= 57)
				{
					String tempString = String.valueOf(postfix.charAt(i));	
					nStack.push(tempString);
				}	
				else
				{
					if (postfix.charAt(i) == '+' || postfix.charAt(i) == '-' || postfix.charAt(i) == '*' ||
							postfix.charAt(i) == '/')
					{	
						moveRight = nStack.pop();
						moveLeft = nStack.pop();				
						answerString = leftPara + moveLeft + postfix.charAt(i) + moveRight + rightPara;
						nStack.push(answerString);	
					}
				}
			}
			answerString = nStack.pop();
		}
		catch (Exception e)
		{
			throw new InvalidNotationFormatException();
		}
		return answerString;
	}
	
	/**
	 * Converts infix to postfix
	 * @param infix format 
	 * @return postfix format
	 * @throws InvalidNotationFormatException throws if string infix is invalid format
	 */
	public static String convertInfixToPostfix(String infix) throws InvalidNotationFormatException
	{
		NotationQueue <String> nQueue = new NotationQueue <String>();
		NotationStack <String> nStack = new NotationStack <String>();
		String finalAnswer = "";
		String temp;
		try 
		{
			for (int i = 0; i < infix.length(); i++)
			{
				temp = "";	
				if (infix.charAt(i) >= 48 && infix.charAt(i) <= 57)
				{
					temp += infix.charAt(i);
					nQueue.enqueue(temp);	
				}
				if (infix.charAt(i) == '+' || infix.charAt(i) == '-' || infix.charAt(i) == '*' || infix.charAt(i) == '/' || infix.charAt(i) == '('
						|| infix.charAt(i) == ')')
				{
					if (nStack.size() == 0 || infix.charAt(i) == '(')
					{
						temp += infix.charAt(i);
						nStack.push(temp);		
					}
					else
					{
						if (infix.charAt(i) == ')')
						{
							boolean hello1 = false;	
							while (hello1 == false)
							{
								if (nStack.top().equals("("))
								{		
									nStack.pop();
									hello1 = true;
									break;
								}		
								nQueue.enqueue(nStack.pop());			
							}
						}
						else
						{
							if(nStack.top().equals("("))
							{
								temp += infix.charAt(i);
								nStack.push(temp);
							}	
							else
							{
								int compare1 = 0;
								int compare2 = 0;
						
								if (infix.charAt(i) == 43|| infix.charAt(i) == 45)
								{
									compare1 = 1;
								}				
								if (infix.charAt(i) == 42|| infix.charAt(i) == 47)
								{
									compare1 = 2;
								}
								if (nStack.top().equals("*") || nStack.top().equals("/"))
								{
									compare2 = 2;
								}
								if (nStack.top().equals("+") || nStack.top().equals("-"))
								{
									compare2 = 1;
								}			
								if (compare1 == compare2 || compare1 < compare2)
								{
									nQueue.enqueue(nStack.pop());
									temp += infix.charAt(i);
									nStack.push(temp);	
								}
								if (compare1 > compare2)
								{
									temp += infix.charAt(i);
									nQueue.enqueue(temp);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new InvalidNotationFormatException();
		}
		try 
		{
			int counter = nQueue.size();
			
			for(int i = 0; i < counter; i++)
			{
				finalAnswer += nQueue.dequeue();			
			}
			for (int ok = 0; ok < nStack.size(); ok++)
			{
				finalAnswer += nStack.pop();
			}
		}
		catch (Exception e)
		{
			throw new InvalidNotationFormatException();
		}
		return finalAnswer;
	}
	
	/**
	 * Evaluates postfix expression and returns answer as double
	 * @param postfixExpr user postfix format to double
	 * @return answer as double
	 * @throws InvalidNotationFormatException throws if string postfix is invalid format
	 */
	public static double evaluatePostfixExpression(String postfixExpr) throws InvalidNotationFormatException
	{
		NotationStack <String> nStack = new NotationStack <String>();
		double finalAnswer = 0;
		double answerDouble = 0;
		String answerString = "";
		double solve;
		double moveRight;
		try 
		{		
			for (int i = 0; i < postfixExpr.length(); i++)
			{
				if (postfixExpr.charAt(i) >= 48 && postfixExpr.charAt(i) <= 57)
				{
					String tempString = String.valueOf(postfixExpr.charAt(i));
					nStack.push(tempString);
				}		
				else
				{
					if (postfixExpr.charAt(i) == '+' || postfixExpr.charAt(i) == '-' || postfixExpr.charAt(i) == '*' ||
							postfixExpr.charAt(i) == '/')
					{	
						try
						{
							solve = 0;
							moveRight = 0;
							answerString = "";
							moveRight = Double.parseDouble(nStack.pop());
							solve = Double.parseDouble(nStack.pop());
						}
						catch (Exception e)
						{
							throw new InvalidNotationFormatException();
						}
						switch(postfixExpr.charAt(i))
						{
						case '+': 
							answerDouble = solve + moveRight;
							break;
						case '-': 
							answerDouble = solve - moveRight;
							break;
						case '*': 
							answerDouble = solve * moveRight;
							break;
						case '/': 
							answerDouble = solve / moveRight;
							break;
						}	
						nStack.push(answerString += answerDouble);
					}
				}
			}	
			if (nStack.size() == 1)
			{
				finalAnswer = Double.parseDouble(nStack.pop());
			}
			
			else
				throw new InvalidNotationFormatException();
		}
		catch (StackOverflowException e)
		{
			e.printStackTrace();
		}
		catch (StackUnderflowException e)
		{
			e.printStackTrace();
		}
		return finalAnswer;
	}
	
	

	/**
	 * Evaluates infix expression and returns answer as double
	 * @param postfixExpr user postfix input
	 * @return returns answer as double
	 * @throws InvalidNotationFormatException throws if string infix is invalid format
	 */
	public static double evaluateInfixExpression (String postfixExpr) throws InvalidNotationFormatException
	{
		double answer = 0;
		try
		{
			String change = convertInfixToPostfix(postfixExpr);
			answer = evaluatePostfixExpression(change);
		}
		catch (Exception e)
		{
			throw new InvalidNotationFormatException();
		}
		return answer;
	}
}
