package com.app.asyctask;

import com.app.exception.AsyncTaskResponseNotFound;

public interface AsyncTaskResponse<T> {
	void processFinish(T output);
}
