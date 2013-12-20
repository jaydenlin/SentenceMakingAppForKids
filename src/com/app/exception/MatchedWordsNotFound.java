package com.app.exception;

public class MatchedWordsNotFound extends Exception{
	
	public MatchedWordsNotFound()
    {
        super();
    }

    public MatchedWordsNotFound(final String argMessage, final Throwable argCause)
    {
        super(argMessage, argCause);
    }

    public MatchedWordsNotFound(final String argMessage)
    {
        super(argMessage);
    }

    public MatchedWordsNotFound(final Throwable argCause)
    {
        super(argCause);
    }
}
