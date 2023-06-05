package MyClassPackage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

public class shaderLoader_Class {

	GL4 gl_ctx;
	
	public shaderLoader_Class(GL4 ctx)// constructor
	{
		gl_ctx = ctx;
	}
	
	
	int loadShaderSource_And_CompileShader(String filePath, int type)
	{
		int sh = 0;
		
		switch(type)
		{
		case 0:
			sh = gl_ctx.glCreateShader(GL4.GL_VERTEX_SHADER);
			break;
		case 1:
			sh = gl_ctx.glCreateShader(GL4.GL_GEOMETRY_SHADER);
			break;
		case 2:
			sh = gl_ctx.glCreateShader(GL4.GL_FRAGMENT_SHADER);
			break;
		case 3:
			sh = gl_ctx.glCreateShader(GL4.GL_COMPUTE_SHADER);
			break;
		default:
			System.out.println("shader type must be 0, 1, 2, 3... error");
			return -1;
		}
		
		
		
		// supply shader source
		try {
			// get shader String from file
			String shaderSource = new String(Files.readAllBytes(Paths.get(filePath)));
			
			// supply shader source
			gl_ctx.glShaderSource(sh, 1, new String[] {shaderSource}, null);
			
			// compile shader
			gl_ctx.glCompileShader(sh);
			
			// error check
			IntBuffer logLength = Buffers.newDirectIntBuffer(1);
			gl_ctx.glGetShaderiv(sh, GL4.GL_INFO_LOG_LENGTH, logLength);
			
			if(logLength.get(0) > 1)
			{
				// alloc buffer
				ByteBuffer logBuf = Buffers.newDirectByteBuffer(logLength.get(0));
				
				// get error log
				gl_ctx.glGetShaderInfoLog(sh, logLength.get(0), null, logBuf);
				
				// declare byte array
				byte[] log = new byte[logLength.get(0)];
				logBuf.get(log);
				
				// convert to string
				String infoLog = new String(log, StandardCharsets.UTF_8);
				
				// print
				System.out.println(infoLog);
				
				// dealloc buffer
				logBuf.clear();
				logBuf = null;
			}
			else
			{
				System.out.println("compile SUCCESS!");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//***********************************
		
		
		return sh;
	}
	
	
	int createProgram_And_AttachShader(int vs, int gs, int fs)
	{
		int PRG = gl_ctx.glCreateProgram();
		
		if( vs != -1 )
		{
			gl_ctx.glAttachShader(PRG, vs);
		}
		if( gs != -1)
		{
			gl_ctx.glAttachShader(PRG, gs);
		}
		if( fs != -1 )
		{
			gl_ctx.glAttachShader(PRG, fs);
		}
		
		// link program
		gl_ctx.glLinkProgram(PRG);
		
		
		// check error
		IntBuffer status = IntBuffer.allocate(1); // on heap memory
		gl_ctx.glGetProgramiv(PRG, GL4.GL_LINK_STATUS, status);
		
		if(status.get(0) == GL4.GL_FALSE)
		{
			// get log length
			IntBuffer logLength = IntBuffer.allocate(1);
			gl_ctx.glGetProgramiv(PRG, GL4.GL_INFO_LOG_LENGTH, logLength);
			
			// alloc buffer
			ByteBuffer logBuf = Buffers.newDirectByteBuffer( logLength.get(0));
		
			// get log
			gl_ctx.glGetProgramInfoLog(PRG, logLength.get(0), null, logBuf);
			
			// convert to String
			byte[] log = new byte[logLength.get(0)];
			logBuf.get(log);
			String infoLog = new String(log, StandardCharsets.UTF_8);
			
			
			// print
			System.out.println(infoLog);
			
			logBuf.clear();
			logBuf = null;
		}
		else if( status.get(0) == GL4.GL_TRUE )
		{
			System.out.println("Program Link SUCCESS!");
		}
		
		return PRG;
	}
	
	
	
	int getUniformLocation(int PRG, String name)
	{
		int UNF = gl_ctx.glGetUniformLocation(PRG, name);
		System.out.println("PRG/" + PRG + "  " + name + "[" + UNF + "]");
		return UNF;
	}
}
