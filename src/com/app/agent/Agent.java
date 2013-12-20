package com.app.agent;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.app.engine.JudgeEngine;

public class Agent implements AgentSubject {

	List<AgentObserver> handlers = new ArrayList<AgentObserver>();

	public Agent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addObserver(AgentObserver agentObserver) {
		// TODO Auto-generated method stub
		handlers.add(agentObserver);
	}

	@Override
	public void removeObserver(AgentObserver agentObserver) {
		// TODO Auto-generated method stub
		if (handlers.contains(agentObserver)) {
			handlers.remove(agentObserver);
		}
	}

	@Override
	public void notifyObservers(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		// for (AgentObserver handler : handlers) {
		// handler.update(judgeEngine,handlersDoneCallback);
		// }
		Log.w(getClass().getSimpleName(),
				"handler size is " + Integer.toString(handlers.size()));
		// set callback to serialize the exec of handlers
		for (int i = 0; i < handlers.size(); i++) {
			// if(i+1<handlers.size()){//may be outof range
			handlers.get(i).setNotifyDoneCallback(new NotifyCallback(i) {

				@Override
				public void doNextHandler(JudgeEngine judgeEngine) {
					// TODO Auto-generated method stub
					Log.w(getClass().getSimpleName(),
							"do next handler handlerIndex is "
									+ Integer.toString(handlerIndex));
					Log.w(getClass().getSimpleName(), "do next handler "
							+ Integer.toString(handlerIndex) + " call "
							+ Integer.toString(handlerIndex + 1));
					if (handlerIndex + 1 < handlers.size()) {
						handlers.get(handlerIndex + 1).update(judgeEngine);
					}
				}

			});
			// }
		}
		// exec handlers
		if (handlers.size() > 0) {
			handlers.get(0).update(judgeEngine);
		} else {
			Log.w(getClass().getSimpleName(), "Observers is empty!");
		}

	}

	public void addHandler(AgentObserver agentObserver) {
		addObserver(agentObserver);
	}

	public void removeHandler(AgentObserver agentObserver) {
		removeObserver(agentObserver);
	}

	public void execHandler(JudgeEngine judgeEngine) {
		notifyObservers(judgeEngine);
	}

}
