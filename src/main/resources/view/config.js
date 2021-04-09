import { GraphicEntityModule } from './entity-module/GraphicEntityModule.js';
import { EndScreenModule } from './endscreen-module/EndScreenModule.js';
import { TooltipModule } from './tooltip-module/TooltipModule.js';
import { ToggleModule } from './toggle-module/ToggleModule.js';

// List of viewer modules that you want to use in your game
export const modules = [
	GraphicEntityModule,
	EndScreenModule,
	TooltipModule,
	ToggleModule
];

export const options = [
  ToggleModule.defineToggle({
    toggle: 'cubeview',
    title: '3D CUBE VIEW',
    values: {
      'TOP FRONT': true,
      'BOTTOM BACK': false
    },
    default: true
  }),
  ToggleModule.defineToggle({
    toggle: 'structuredCube',
    title: 'STRUCTURED CUBE',
    values: {
      'SHOW': true,
      'HIDE': false
    },
    default: true
  }),
  ToggleModule.defineToggle({
    toggle: 'message',
    title: 'MESSAGE',
    values: {
      'SHOW': true,
      'HIDE': false
    },
    default: true
  })
]