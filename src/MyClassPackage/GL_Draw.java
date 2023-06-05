package MyClassPackage;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class GL_Draw implements GLEventListener{

	
	mainController mc_obj;
	matrixClass matrix_obj = new matrixClass();
	GL4 gl_ctx;
	
	shaderLoader_Class shader_obj;
	
	// sampler name
	IntBuffer Sampler_name = Buffers.newDirectIntBuffer(1);
	
	// VAO, VBO
	IntBuffer VAO_Grid = Buffers.newDirectIntBuffer(1);
	IntBuffer VBO_Grid = Buffers.newDirectIntBuffer(1); // vert,
	IntBuffer VAO_STL = Buffers.newDirectIntBuffer(1);
	IntBuffer VBO_STL = Buffers.newDirectIntBuffer(2); // v, n
	
	// shader
	int VS_SINGLE;
	int FS_SINGLE;
	int PRG_SINGLE;
	int UNF_SINGLE_mvpMatrix;
	int UNF_SINGLE_singleColor;
	
	// Grid buffer
	float[] GridVert = new float[3000];
	FloatBuffer GridBuf;
	
	public GL_Draw()// constructor
	{
		System.out.println("GL_Draw constructed");
		
		// setup buffer
		for(int i = 0 ; i < 3000 ; i++ )
		{GridVert[i] = 0.0f;}
		
		GridBuf = FloatBuffer.allocate(3000); // not size, num elements
	}
	
	
	
	
	// GLEventListener method *******************
	public void display(GLAutoDrawable arg0)
	{
		//System.out.println("display");
		GL4 gl = arg0.getGL().getGL4();
		
		// set viewport
		gl.glViewport(0, 0, 800, 650);
		
		// init & setup matrix
		matrix_obj.initMatrix();
		matrix_obj.lookAt( 0.0f, 0.0f,  600.0f,
							0.0f, 0.0f, 0.0f,
							0.0f, 1.0f, 0.0f);
		matrix_obj.perspective(75.0f, 800.0f/650.0f, 0.1f, 1000.0f);
		
		// get random float
		gl.glClearColor((float) Math.random(), 0.9f, 0.9f, 1.0f);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		
		//****************************************
		// draw grid
		gl.glUseProgram(PRG_SINGLE);
		
		// setup Uniform
		gl.glUniformMatrix2x4fv(UNF_SINGLE_singleColor, 1, false, matrix_obj.getMatrixAsFloatBuffer());
		gl.glUniform4f(UNF_SINGLE_singleColor, 1.0f, 1.0f, 0.0f, 1.0f);
		gl.glLineWidth(3.0f);
		
		// update buffer
		int nv = this.setup_grid_buffer();
		long dataSize = nv*3*Float.BYTES; // sizeof(float) = 4
		System.out.println("pos" + GridBuf.position());
		gl.glBindVertexArray(VAO_Grid.get(0));
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, VBO_Grid.get(0));
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, dataSize, GridBuf, GL4.GL_DYNAMIC_DRAW);
		gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0);
		
		gl.glDrawArrays(GL4.GL_LINES, 0, nv);
		//****************************************
		
		
		gl.glUseProgram(0);
		
		
		gl.glFinish();
		//arg0.swapBuffers();
		
	}
	
	public void dispose(GLAutoDrawable arg0)
	{
		System.out.println("dispose");
	}
	
	public void init(GLAutoDrawable arg0)
	{
		System.out.println("GL init");
		
		GL4 gl = arg0.getGL().getGL4();
		gl_ctx = gl;
		
		shader_obj = new shaderLoader_Class(gl);
		
		// openGL setup
		this.setup_status(gl);
		this.setup_sampler(gl);
		this.setup_VAO_VBO(gl);
		this.setup_shader(gl);
		this.setup_FBO(gl);
	}
	
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
	{
		//System.out.printf("reshape %d, %d, %d, %d", arg1, arg2, arg3, arg4);
	}
	// EventListener method *******************
	
	
	// setup status
	void setup_status(GL4 gl)
	{
		gl.glCullFace(GL4.GL_BACK);
	}
	
	// setup funtion
	void setup_sampler(GL4 gl)
	{
		System.out.println("setup Samplers");
		
		gl.glGenSamplers(1, Sampler_name);
		int smpID = Sampler_name.get(0);
		gl.glSamplerParameteri(smpID, GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
		gl.glSamplerParameteri(smpID, GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
		gl.glSamplerParameteri(smpID, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR);
		gl.glSamplerParameteri(smpID, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR);
	
		IntBuffer numTex = Buffers.newDirectIntBuffer(1);
		gl.glGetIntegerv(GL4.GL_MAX_TEXTURE_IMAGE_UNITS, numTex);
		
		int tempNum = numTex.get(0);
		System.out.println("num texture units : " + tempNum);
		
		for( int i =0 ; i < tempNum ; i++ )
		{
			gl.glBindSampler(i, smpID);
		}
	} // setup sampler
	
	
	
	void setup_VAO_VBO(GL4 gl)
	{

		// for GRID (Dynamic)
		gl.glGenVertexArrays(1, VAO_Grid);
		gl.glGenBuffers(1,  VBO_Grid);
		
		gl.glBindVertexArray(VAO_Grid.get(0));
		gl.glEnableVertexAttribArray(0); // vert only
		
		// for STL ( Dynamic )
		gl.glGenVertexArrays(1, VAO_STL);
		gl.glGenBuffers(2, VBO_STL);
		
		gl.glBindVertexArray(VAO_STL.get(0));
		gl.glEnableVertexAttribArray(0); // vert
		gl.glEnableVertexAttribArray(1); // norm
		
		//
		//gl_ctx.glBindVertexArray(0);
	}
	
	
	void setup_shader(GL4 gl)
	{
		VS_SINGLE = shader_obj.loadShaderSource_And_CompileShader("resources/VS_SINGLE.txt", 0);
		FS_SINGLE = shader_obj.loadShaderSource_And_CompileShader("resources/FS_SINGLE.txt", 2);
		PRG_SINGLE = shader_obj.createProgram_And_AttachShader(VS_SINGLE, -1, FS_SINGLE);
		UNF_SINGLE_mvpMatrix = shader_obj.getUniformLocation(PRG_SINGLE, "mvpMatrix");
		UNF_SINGLE_singleColor = shader_obj.getUniformLocation(PRG_SINGLE, "singleColor");
	}
	
	void setup_FBO(GL4 gl)
	{
		// set to Default framebuffer
		//gl_ctx.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
	}
	
	
	// grid buffer
	int setup_grid_buffer()
	{
		GridBuf.position(0);
		
		int printerID = mc_obj.Selected_Printer_ID;
		int bedX, bedY, bedZ;
		
		switch(printerID)
		{
		case 0:
			bedX = mc_obj.A_X;
			bedY = mc_obj.A_Y;
			bedZ = mc_obj.A_Z;
			break;
		case 1:
			bedX = mc_obj.B_X;
			bedY = mc_obj.B_Y;
			bedZ = mc_obj.B_Z;
			break;
		case 2:
			bedX = mc_obj.C_X;
			bedY = mc_obj.C_Y;
			bedZ = mc_obj.C_Z;
			break;
		default:
			bedX = mc_obj.A_X;
			bedY = mc_obj.A_Y;
			bedZ = mc_obj.A_Z;
			break;
		}
		
		int vertCount = 0;
		int iterX = (bedX/10)+1;
		int iterY = (bedY/10)+1;
		int ID=0;
		float startX = -(float)(bedX/2);
		float startY = -(float)(bedY/2);
		float endX = (float)(bedX/2);
		float endY = (float)(bedY/2);
		
		// x line
		for( int i = 0 ; i < iterX ; i++ )
		{
			// vert 1
			GridVert[ID] = startX + (10.0f*i); ID++;
			GridVert[ID] = startY;	ID++;
			GridVert[ID] = 0.0f;	ID++;
			GridBuf.put(startX + (10.0f*i));
			GridBuf.put(startY);
			GridBuf.put(0.0f);
			// vert 2
			GridVert[ID] = startX + (10.0f*i);	ID++;
			GridVert[ID] = endY;	ID++;
			GridVert[ID] = 0.0f;	ID++;
			GridBuf.put(startX + (10.0f*i));
			GridBuf.put(endY);
			GridBuf.put(0.0f);
			
			// add 2 vertex
			vertCount += 2;
		}
		
		
		// y line
		for(int i = 0 ; i < iterY ; i++ )
		{
			// vert1
			GridVert[ID] = startX;	ID++;
			GridVert[ID] = startY + (10.0f*i);	ID++;
			GridVert[ID] = 0.0f;	ID++;
			GridBuf.put(startX);
			GridBuf.put(startY+(10.0f*i));
			GridBuf.put(0.0f);
			
			// vert2
			GridVert[ID] = endX;	ID++;
			GridVert[ID] = startY + (10.0f*i);	ID++;
			GridVert[ID] = 0.0f;	ID++;
			GridBuf.put(endX);
			GridBuf.put(startY+(10.0f*i));
			GridBuf.put(0.0f);
			
			// add 2 vertex
			vertCount += 2;
		}
		
		GridBuf.position(0);
		//GridBuf.flip();

		
		return vertCount;
	}
}
