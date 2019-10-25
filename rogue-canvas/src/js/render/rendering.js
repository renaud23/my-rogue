const createRenderer = (canvas) => {
	const backBuffer = document.createElement('canvas');
	backBuffer.width = canvas.width;
	backBuffer.height = canvas.height;
	const bufferContext = backBuffer.getContext('2d');
	const canvasContext = canvas.getContext('2d');

	const render = () => {
		canvasContext.drawImage(backBuffer, 0, 0);
	};

	const fillRect = createFillRect(bufferContext);

	const clear = (color = '#FF0000') => {
		fillRect(color, 0, 0, backBuffer.width, backBuffer.height);
	};

	const drawTexture = createTextureDrawer(bufferContext);

	return {
		render,
		drawTexture,
		fillRect,
		clear
	};
};

export const createTexture = (url) => {
	const texture = document.createElement('img');
	texture.src = url;
	return texture;
};

const createTextureDrawer = (context) => (texture, tx, ty, tw, th, x, y, w, h) => {
	context.drawImage(texture, tx, ty, tw, th, x, y, w, h);
};

const createFillRect = (context) => (color = '#FF0000', x, y, w, h) => {
	context.fillStyle = color;
	context.fillRect(x, y, w, h);
};

export default createRenderer;
