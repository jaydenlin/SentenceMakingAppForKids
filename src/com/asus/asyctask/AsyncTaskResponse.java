package com.asus.asyctask;

public interface AsyncTaskResponse<T> {
	void processFinish(T output);
}
