package com.gameapp.trialproject;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class GameFactory implements EntityFactory {
  @Spawns("background")
  public Entity newBackground(SpawnData data) {
    return FXGL.entityBuilder(data)
        .at(0,0)
        .view(FXGL.texture("background.png", 600, 600))
        .zIndex(-100)
        .build();
  }
}
