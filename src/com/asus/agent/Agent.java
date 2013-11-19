package com.asus.agent;

import java.util.ArrayList;
import java.util.List;

import com.asus.engine.JudgeEngine;

public class Agent implements AgentSubject{
	
	List<AgentObserver> handlers=new ArrayList<AgentObserver>();
	
	public Agent(JudgeEngine judgeEngine) {
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
		if(handlers.contains(agentObserver)){
			handlers.remove(agentObserver);
		}
	}

	@Override
	public void notifyObservers(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		for (AgentObserver handler : handlers) {
			handler.update(judgeEngine);
		}
	}
	
	public void addHandler(AgentObserver agentObserver){
		addObserver(agentObserver);
	}
	
	public void removeHandler(AgentObserver agentObserver){
		removeObserver(agentObserver);
	}
	
	public void execHandler(JudgeEngine judgeEngine) {
		notifyObservers(judgeEngine);
	}

}
