package com.asus.music;

import android.drm.DrmStore.Playback;

import com.asus.agent.AgentObserver;
import com.asus.engine.JudgeEngine;

public abstract class MusicHandler extends AgentObserver{

	public abstract void play();
}
