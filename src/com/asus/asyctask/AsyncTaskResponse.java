package com.asus.asyctask;

import com.asus.exception.AsyncTaskResponseNotFound;

public interface AsyncTaskResponse<T> {
	void processFinish(T output);
}
