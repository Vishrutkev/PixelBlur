import java.util.Arrays;

/**
 * @author Vishrut Kevadiya
 * The class is meant to illustrate a couple of image-processing algorithms:
 * Gaussian blurring and simple edge detection
 */
public class ImageFilter{
	//TODO prevent object creation, as this is a utility class
	private ImageFilter() {
		
	}
	
	/**
	 * Method implements Gaussian blurring
	 * @param imageData array of image pixels
	 * @param width image width
	 * @param sigma parameter of the Gaussian distribution
	 */
	public static void blur (int[] imageData, int width, double sigma){
		//TODO your task is to replace this stub code with the proper implementation of the method
		//create a temporary array to store the result
		
		final int MAX_KERNEL_SIZE = 15;
		
		double[] kernel = new double [MAX_KERNEL_SIZE];
		
		for (int i = 0; i <= MAX_KERNEL_SIZE / 2 ; i++){
			kernel [MAX_KERNEL_SIZE / 2 + i] = Math.exp(-0.5 * i * i / sigma / sigma);
			kernel [MAX_KERNEL_SIZE / 2 - i] = Math.exp(-0.5 * i * i / sigma / sigma);
		}
		double kernelSum = 0;
		for (int i = 0; i < MAX_KERNEL_SIZE; i++) kernelSum += kernel [i]; //compute the sum
		for (int i = 0; i < MAX_KERNEL_SIZE; i++) kernel [i] /= kernelSum; //normalize by that sum
		System.out.println(Arrays.toString(kernel));
		
		
		
		int[] resultImageData = new int[imageData.length]; //TODO NO, it should not be null!
		//TODO apply convolution in one dimension
		int arrLength = imageData.length / width;
		
		int[][] tempArryx = new int [arrLength][width];
		int[][] tempArryY = new int [arrLength][width];
		
		int countx = 0;
		for (int i = 0; i < arrLength; i++) {
			for (int j = 0; j < width; j++) {
				tempArryx[i][j] = imageData[countx++];
			}
		}

		
		int num = 0;
		int imageDataIdx = 0;
		int red, green, blue;
		
			
		for (int i = 0; i < tempArryx.length; i++) {
			for (int j = 0; j < tempArryx[i].length; j++) {
				int output = 0;
				int increment = 0;
			
				for (int k = 0; k < 15; k++) {
					
					imageDataIdx = j + increment++ - ((kernel.length)/2);
					
					if (imageDataIdx < 0) {
						imageDataIdx = 0;
					}else if (imageDataIdx > tempArryx[0].length) {
						imageDataIdx = tempArryx.length-1;
					}
	
			
					if ((imageDataIdx >= 0) && (imageDataIdx < tempArryx[0].length)) {
						
						red 	= (tempArryx[i][imageDataIdx] & 0x00FF0000)>>16;
						green 	= (tempArryx[i][imageDataIdx] & 0x0000FF00)>>8;
						blue 	= (tempArryx[i][imageDataIdx] & 0x000000FF);
						
						double kernelRed = red *  kernel[k];
						double kernelGreen = green * kernel[k];
						double kernelBlue = blue * kernel[k];
						
						 red = (int) kernelRed;
						 green = (int) kernelGreen;
						 blue = (int) kernelBlue;
						 
						
					    output += red << 16 | green <<8 | blue;
					    
					}
					   
					
				}
				
				resultImageData[num++] = output;
		    }
		}
		
		

		int county = 0;
		for (int i = 0; i < arrLength; i++) {
			for (int j = 0; j < width; j++) {
				tempArryY[i][j] = resultImageData[county++];
			}
		}
		
		
		int numy = 0;
		int imageDataIdy = 0;
		int redy, greeny, bluey;
		
			
		for (int i = 0; i < tempArryY.length; i++) {
			for (int j = 0; j < tempArryY[i].length; j++) {
				int outputy = 0;
				int increment = 0;
			
				for (int k = 0; k < 15; k++) {
					
					imageDataIdy = i + increment++ - ((kernel.length)/2);
					
					if (imageDataIdy < 0) {
						imageDataIdy = 0;
					}else if (imageDataIdy > tempArryY.length) {
						imageDataIdy = tempArryY.length-1;
					 }
	
			
					if ((imageDataIdy >= 0) && (imageDataIdy < tempArryY.length)) {
						
						redy 	= (tempArryY[imageDataIdy][j] & 0x00FF0000)>>16;
						greeny 	= (tempArryY[imageDataIdy][j] & 0x0000FF00)>>8;
						bluey 	= (tempArryY[imageDataIdy][j] & 0x000000FF);
						
						double kernelRedy = redy *  kernel[k];
						double kernelGreeny = greeny * kernel[k];
						double kernelBluey = bluey * kernel[k];
						
						 redy = (int) kernelRedy;
						 greeny = (int) kernelGreeny;
						 bluey = (int) kernelBluey;
						
				
					    outputy += redy << 16 | greeny <<8 | bluey;
					    
					}
					  
					
				}
				
				resultImageData[numy++] = outputy;
		    }
		}
		
		
		//TODO store the result back in the original imageData array
		for (int i = 0; i < imageData.length; i++) {
			imageData[i] = resultImageData[i];
		}
		//one way to store the result back 
		//System.arraycopy(resultImageData, 0, imageData, 0, imageData.length);
	}

	/**
	 * Method implements simple edge detection
	 * @param imageData imageData array of image pixels
	 * @param width image width
	 */
	

	
	public static void edgeDetection(int[] imageData, int width) {
		//TODO your task is to replace this stub code with the proper implementation of the method 
		//The code below merely demonstrates how to extract RGB pixel values from the image and how to write them them back
		for (int i=0; i<imageData.length; i++){
			int red, green, blue;
			red 	= (imageData[i] & 0x00FF0000)>>16; //try 0.0 * (imageData[i] & 0x00FF0000)>>16 here
			green 	= (imageData[i] & 0x0000FF00)>>8;
			blue 	= (imageData[i] & 0x000000FF);

		//one way to store the result back
		imageData[i] = red<<16 | green <<8 | blue;
		}
		
		//width = 640;
	
		int arrLengthe = imageData.length / width;
		int[][] tempArrye = new int [arrLengthe][width];
		int output = 0;
		int[][]kernel = {{-1,-2,-1},
						 {0, 0, 0},
						 {1, 2, 1}};
		int num = 0;
		
		int counte = 0;
		for (int i = 0; i < arrLengthe; i++) {
			for (int j = 0; j < width; j++) {
				tempArrye[i][j] = imageData[counte++];
			}
		}
		
		
		
		int outputArry[] = new int[imageData.length];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < arrLengthe; j++) {
			
				output = 0;
			    
			    int red = 0, green = 0, blue = 0;
			    double kernelred = 0.0, kernelgreen = 0.0, kernelblue = 0.0;
			   for (int ki = 0; ki < kernel.length; ki++) {
					for (int kj = 0; kj < kernel[ki].length; kj++) {
						
						
						int numX = j - (kernel.length)/2 + kj;
					   
					    
					    if (numX < 0) {
					    	numX = 0;
					    }
					    else if (numX > tempArrye[0].length) {
					    	numX = tempArrye[i].length - 1;
					    }
					    
					    int numY = i - (kernel.length)/2 + ki;
					     if (numY < 0) {
					    	numY = 0;
					    }
					  
					    else if (numY > arrLengthe) {
					    	 numY = arrLengthe - 1;
					     }
					    
					    if (numX >= 0  && numX < width && numY >= 0 && numY < arrLengthe) {
					    	red 	+= (tempArrye[numY][numX] & 0x00FF0000)>>16;
						 	green 	+= (tempArrye[numY][numX] & 0x0000FF00)>>8;
						 	blue 	+= (tempArrye[numY][numX] & 0x000000FF);
					   
							 kernelred += kernel[ki][kj] * red;
    						 kernelgreen += kernel[ki][kj] * green;
    				         kernelblue += kernel[ki][kj] * blue;
    						 
    						 int redv = (int) kernelred;
    						 int greenv = (int) kernelgreen;
    						 int bluev = (int) kernelblue;
    				 	
    						if (redv < 0) redv = 0;
    						if (redv > 255) redv = 255;
    					
    						if (bluev < 0) bluev = 0;
    						if (bluev > 255) bluev = 255;
    						
    						if (greenv < 0) greenv = 0;
    						if (greenv > 255) greenv = 255;
    						
    					    output += redv << 16 | greenv << 8 | bluev;
					    	
	        						 
	                     }
					
					 }
					
				}
				
				outputArry[num++] = output;
					
			
				
			}	
		}
		

		
	
		for (int i = 0; i < imageData.length; i++) {
			imageData[i] = outputArry[i];
		}
			
	
		}
	
	
	}
	
	


	



