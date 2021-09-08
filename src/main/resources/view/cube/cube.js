import * as THREE from './three/three.module.js'
import * as orbit from './three/OrbitControls.js'

const White = 0xffffff;
const Red = 0xe10000;
const Yellow = 0xf4f60d;
const Orange = 0xf6830d;
const Blue = 0x0d1df6;
const Green = 0x38f60d;
const NONE = 0x00000;
var SIZE = 3;
let gap = 0.15;

var rowRotationAxis   = new THREE.Vector4(0,1,0,0);
var colRotationAxis   = new THREE.Vector4(1,0,0,0);
var sliceRotationAxis = new THREE.Vector4(0,0,1,0);

var turnAnglePerFrame = (Math.PI/2);

let cubes = []
let scene = new THREE.Scene();
let camera = new THREE.PerspectiveCamera( 75, 16/9, 0.1, 1000 );
let renderer = new THREE.WebGLRenderer({alpha : true, antialias:true});
let controls = new orbit.OrbitControls(camera, renderer.domElement);
let k = 3;
let faces = [
	//F start
	[0, 2, 2, 4],
	[1, 2, 2, 4],
	[2, 2, 2, 4],
	[0, 1, 2, 4],
	[1, 1, 2, 4],
	[2, 1, 2, 4],
	[0, 0, 2, 4],
	[1, 0, 2, 4],
	[2, 0, 2, 4],
	//F end
	//R start
	[2, 2, 2, 0],
	[2, 2, 1, 0],
	[2, 2, 0, 0],
	[2, 1, 2, 0],
	[2, 1, 1, 0],
	[2, 1, 0, 0],
	[2, 0, 2, 0],
	[2, 0, 1, 0],
	[2, 0, 0, 0],
	//R end
	//B start
	[2, 2, 0, 5],
	[1, 2, 0, 5],
	[0, 2, 0, 5],
	[2, 1, 0, 5],
	[1, 1, 0, 5],
	[0, 1, 0, 5],
	[2, 0, 0, 5],
	[1, 0, 0, 5],
	[0, 0, 0, 5],
	//B end
	//L start
	[0, 2, 0, 1],
	[0, 2, 1, 1],
	[0, 2, 2, 1],
	[0, 1, 0, 1],
	[0, 1, 1, 1],
	[0, 1, 2, 1],
	[0, 0, 0, 1],
	[0, 0, 1, 1],
	[0, 0, 2, 1],
	//L end
	//U start
	[0, 2, 0, 2],
	[1, 2, 0, 2],
	[2, 2, 0, 2],
	[0, 2, 1, 2],
	[1, 2, 1, 2],
	[2, 2, 1, 2],
	[0, 2, 2, 2],
	[1, 2, 2, 2],
	[2, 2, 2, 2],
	//U end
	//D start
	[0, 0, 2, 3],
	[1, 0, 2, 3],
	[2, 0, 2, 3],
	[0, 0, 1, 3],
	[1, 0, 1, 3],
	[2, 0, 1, 3],
	[0, 0, 0, 3],
	[1, 0, 0, 3],
	[2, 0, 0, 3],
	//D end
]
export class animator {

  constructor (viewer) {
    this.container = viewer;
  }
  
  static get name() {
      return 'cube'
  }

  formCube() {
    for (let i = 0; i < cubes.length; i++) scene.remove(cubes[i]);
    cubes = []
    for (let i = 0; i < SIZE*SIZE*SIZE; i++) {
		var x = (i%SIZE)
		var y = ( ( ( i/(SIZE  ) ) | 0 ) % SIZE);
		var z = ( ( ( i/(SIZE*SIZE) ) | 0 ) % SIZE);
		var geometry = new THREE.BoxGeometry(1, 1, 1);
		var material = new THREE.MeshBasicMaterial({color: NONE});
		var cube = new THREE.Mesh(geometry, [material, material, material, material, material, material]);
		cube.position.x = (1+gap)*(x-1);
		cube.position.y = (1+gap)*(y-1);
		cube.position.z = (1+gap)*(z-1);
		scene.add(cube);
		cubes.push(cube);
    }
  }

  starter() {
    renderer.setSize(window.innerWidth, window.innerWidth * 9/16);
    this.container.appendChild(renderer.domElement);
    this.formCube();
    camera.position.z = 4;
    camera.position.x = 3;
    camera.position.y = 2;
	function update() {
		renderer.setSize(window.innerWidth, window.innerWidth * 9/16);
		controls.update();
		renderer.render(scene, camera);
		requestAnimationFrame(update);
	}
    update();
  }

  getColor(c) {
	  if (c == "w") return White;
	  if (c == "r") return Red;
	  if (c == "y") return Yellow;
	  if (c == "o") return Orange;
	  if (c == "b") return Blue;
	  if (c == "g") return Green;
	  return NONE;
  }

  setState(state) {
	for (let i = 0; i < faces.length; i++) {
		let color = this.getColor(state[i]);
		let idx = faces[i][0] + (faces[i][1] * SIZE) + (faces[i][2] * (SIZE*SIZE));
		cubes[idx].material[faces[i][3]] = new THREE.MeshBasicMaterial({color: color});
	}
  }

  frame(action, prog, speed) {
    turnAnglePerFrame = (Math.PI/2) * prog;
    if (action == "R") {
        rotateCol(2, -turnAnglePerFrame);
    } else if (action == "Ri") {
        rotateCol(2, turnAnglePerFrame);
    } else if (action == "R2") {
        rotateCol(2, -turnAnglePerFrame * 2);
    } else if (action == "L") {
        rotateCol(0, turnAnglePerFrame);
    } else if (action == "Li") {
        rotateCol(0, -turnAnglePerFrame);
    } else if (action == "L2") {
        rotateCol(0, turnAnglePerFrame * 2);
    } else if (action == "U") {
        rotateRow(2, -turnAnglePerFrame);
    } else if (action == "Ui") {
        rotateRow(2, turnAnglePerFrame);
    } else if (action == "U2") {
        rotateRow(2, -turnAnglePerFrame * 2);
    } else if (action == "D") {
        rotateRow(0, turnAnglePerFrame);
    } else if (action == "Di") {
        rotateRow(0, -turnAnglePerFrame);
    } else if (action == "D2") {
        rotateRow(0, turnAnglePerFrame * 2);
    } else if (action == "F") {
        rotateSlice(2, -turnAnglePerFrame);
    } else if (action == "Fi") {
        rotateSlice(2, turnAnglePerFrame);
    } else if (action == "F2") {
        rotateSlice(2, -turnAnglePerFrame * 2);
    } else if (action == "B") {
        rotateSlice(0, turnAnglePerFrame);
    } else if (action == "Bi") {
        rotateSlice(0, -turnAnglePerFrame);
    } else if (action == "B2") {
        rotateSlice(0, turnAnglePerFrame * 2);
    }
  }
  	end(prog, url) {
		  for (let i = 0; i < k*k*k; i++) {
			var x = (i%SIZE)
			var y = ( ( ( i/(SIZE  ) ) | 0 ) % SIZE);
			var z = ( ( ( i/(SIZE*SIZE) ) | 0 ) % SIZE);
			x -= 1
			y -= 1
			z -= 1
			cubes[i].position.x = x + (5*prog*x);
			cubes[i].position.y = y + (5*prog*y) - 3*prog;
			cubes[i].position.z = z + (5*prog*z);
		  }
	}
	
	firstEnd(url) {
		for (let i = 0; i < k*k*k; i++) {
			var textureLoader = new THREE.TextureLoader();
			var crateTexture = textureLoader.load(url);
			scene.remove(cubes[i])
			cubes[i] = new THREE.Mesh(
				new THREE.BoxGeometry(1, 1, 1),
				new THREE.MeshBasicMaterial({
					map: crateTexture
				}) 
				);
			cubes[i].material.side = THREE.DoubleSide;
			scene.add(cubes[i]);
		}
	}
}

// 0 <= row < k
function rotateRow(row, angle)
{
	for (var i = 0; i < k*k; i++) {
		var ind = k*row + (i%k) + (((i/k)|0)*(k*k));
 		cubes[ind].position.applyMatrix4(new THREE.Matrix4().makeRotationAxis( rowRotationAxis, angle ));
  		cubes[ind].rotateOnAxis(cubes[ind].worldToLocal(rowRotationAxis.clone()), angle);
	}
}

// 0 <= col < k
function rotateCol(col, angle)
{
	for (var i = col; i < cubes.length; i+=k) {
		cubes[i].position.applyMatrix4(new THREE.Matrix4().makeRotationAxis( colRotationAxis, angle ));
		cubes[i].rotateOnAxis(cubes[i].worldToLocal(colRotationAxis.clone()), angle);
	}
}

// 0 <= slice < k
function rotateSlice(slice, angle)
{
	for (var i = slice*k*k; i < (slice+1)*k*k; i++) {
		cubes[i].position.applyMatrix4(new THREE.Matrix4().makeRotationAxis( sliceRotationAxis, angle ));
		cubes[i].rotateOnAxis(cubes[i].worldToLocal(sliceRotationAxis.clone()), angle);
	}
}