import { animator } from './cube/cube.js'

let lastProgress = 0;
let lastTurn = 1;

export class CubeViewer {
  viewer = document.body.children[0].children[1].children[0];
  anime = new animator(this.viewer)
  initState = "Nan";

  static get name () {
    return 'cubeviewer'
  }
  /**
   * Called when data is received.
   * Handles data for the given frame. Returns data that will be sent as parameter to updateScene.
   * @param frameInfo information about the current frame.
   * @param frameData data that has been sent from the Java module.
   */

  handleFrameData (frameInfo, frameData) {
    // Handle your data here
    frameInfo["data"] = frameData;
    // Return what is necessary to your module
    return frameInfo;
  }

  /**
   * Called when global data is received. Should only be called on init.
   * @param players information about players, such as avatar, name, color..
   * @param globalData data that has been sent from the Java module.
   */
  handleGlobalData (players, globalData) {
    if (globalData == undefined) {
      return null;
    }
    let data = globalData;
    this.initState = data;
    this.anime.starter();
    this.anime.setState(data);
  }

  /**
   * Called when the scene needs an update.
   * @param previousData data from the previous frame.
   * @param currentData data of the current frame.
   * @param progress progress of the frame. 0 <= progress <= 1
   * @param speed the speed of the viewer, setted up by the user.
   */
  updateScene (previousData, currentData, progress, speed) {
    if (currentData.number == 0) {
      if (this.initState == "NAN") return null;
      lastProgress = 0;
      lastTurn = 1;
      this.anime.formCube();
      this.anime.setState(this.initState);
      this.anime.frame("Nan", 0);
      return null;
    }
    if (currentData.data == undefined) return null;
    const frameData = currentData.data
    if (currentData.number != lastTurn) {
      lastProgress = 0;
      this.anime.formCube();
      this.anime.setState(frameData.substr(0, 54))
      lastTurn = currentData.number;
    }
    this.anime.frame(frameData.slice(54), progress - lastProgress, speed);
    lastProgress = progress;
    lastTurn = currentData.number;
  }

  /**
   * Called when the viewer needs to be rerendered (init phase, resized viewer).
   * @param container a PIXI Container. Add your elements to this object.
   * @param canvasData canvas data containing width and height.
   */
  reinitScene (container, canvasData) {
  }

  /**
   * Called every delta milliseconds.
   * @param delta time between current and last call. Aproximately 16ms by default.
   */
  animateScene (delta) {

  }
}