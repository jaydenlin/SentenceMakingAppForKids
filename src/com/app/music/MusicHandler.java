package com.app.music;

import android.drm.DrmStore.Playback;

import com.app.agent.AgentObserver;
import com.app.engine.JudgeEngine;

public abstract class MusicHandler extends AgentObserver{

	public abstract void play();
}
