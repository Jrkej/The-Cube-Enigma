import { GraphicEntityModule } from './entity-module/GraphicEntityModule.js';
import { EndScreenModule } from './endscreen-module/EndScreenModule.js';
import { TooltipModule } from './tooltip-module/TooltipModule.js';
import { ToggleModule } from './toggle-module/ToggleModule.js';
import { CubeViewer } from './CubeView.js';

// List of viewer modules that you want to use in your game
export const modules = [
	GraphicEntityModule,
	EndScreenModule,
	TooltipModule,
	ToggleModule,
	CubeViewer
];

export const options = [
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