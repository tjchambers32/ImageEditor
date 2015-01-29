import java.util.*;
import java.io.*;

public class PPMParser {

	int height = 0;
	int width = 0;
	int maxColorValue = 0;

	Scanner scanner;
	Pixel[][] fullImage;

	public PPMParser(String fileName) throws FileNotFoundException {
		
		FileReader FR = new FileReader(fileName);
		BufferedReader sourceFile = new BufferedReader(FR);
		parse(sourceFile);
	}

	private void parse(BufferedReader sourceFile) throws FileNotFoundException {
		
		scanner = new Scanner(sourceFile);

		scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s+)");

		scanner.next(); // get past MagicNumber

		width = Integer.parseInt(scanner.next());
		height = Integer.parseInt(scanner.next());
		maxColorValue = Integer.parseInt(scanner.next());

		fullImage = new Pixel[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Pixel tempPixel = new Pixel();
				tempPixel.setRed(scanner.nextInt());
				tempPixel.setGreen(scanner.nextInt());
				tempPixel.setBlue(scanner.nextInt());
				fullImage[i][j] = tempPixel;
			}
		}

		scanner.close();
		return;

	}

	void grayscale() {
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int totalColor, average = 0;
				totalColor = fullImage[i][j].getRed()
						+ fullImage[i][j].getGreen()
						+ fullImage[i][j].getBlue();
				average = totalColor / 3;
				fullImage[i][j].setRed(average);
				fullImage[i][j].setGreen(average);
				fullImage[i][j].setBlue(average);
			}
		}

		return;
	}

	void invert() {
		
		for (int i = 0; i < height; i++) {
			
			for (int j = 0; j < width; j++) {
				
				int rColor, gColor, bColor = 0;
				int invertRed, invertGreen, invertBlue = 0;
				rColor = fullImage[i][j].getRed();
				gColor = fullImage[i][j].getGreen();
				bColor = fullImage[i][j].getBlue();

				invertRed = 255 - rColor;
				invertGreen = 255 - gColor;
				invertBlue = 255 - bColor;

				if (invertRed > 255)
					invertRed = 255;
				else if (invertRed < 0)
					invertRed = 0;
				if (invertGreen > 255)
					invertGreen = 255;
				else if (invertGreen < 0)
					invertGreen = 0;
				if (invertBlue > 255)
					invertBlue = 255;
				else if (invertBlue < 0)
					invertBlue = 0;

				fullImage[i][j].setRed(invertRed);
				fullImage[i][j].setGreen(invertGreen);
				fullImage[i][j].setBlue(invertBlue);
			}
		}

		return;
	}

	void emboss() {
		// start in bottom right, and work up/left so we don't lose information
		for (int i = height - 1; i >= 0; i--) // ignore top row
		{
			for (int j = width - 1; j >= 0; j--) // ignore left column
			{
				if ((i == 0) || (j == 0)) {
					fullImage[i][j].setRed(128);
					fullImage[i][j].setGreen(128);
					fullImage[i][j].setBlue(128);
				} else {

					int PRed, ULPRed = 0;
					int PGreen, ULPGreen = 0;
					int PBlue, ULPBlue = 0;

					PRed = fullImage[i][j].getRed();
					ULPRed = fullImage[i - 1][j - 1].getRed();

					PGreen = fullImage[i][j].getGreen();
					ULPGreen = fullImage[i - 1][j - 1].getGreen();

					PBlue = fullImage[i][j].getBlue();
					ULPBlue = fullImage[i - 1][j - 1].getBlue();

					int redDiff, greenDiff, blueDiff, maxDiff = 0;
					redDiff = PRed - ULPRed;
					greenDiff = PGreen - ULPGreen;
					blueDiff = PBlue - ULPBlue;

					
					if (Math.abs(redDiff) >= Math.abs(greenDiff))
						maxDiff = redDiff;
					else
						maxDiff = greenDiff;

					if (Math.abs(maxDiff) < Math.abs(blueDiff))
						maxDiff = blueDiff;

					maxDiff += 128;

					if (maxDiff > 255)
						maxDiff = 255;
					else if (maxDiff < 0)
						maxDiff = 0;

					fullImage[i][j].setRed(maxDiff);
					fullImage[i][j].setGreen(maxDiff);
					fullImage[i][j].setBlue(maxDiff);
				}
			}
		}
	}

	void motionBlur(int blurLength) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				
				int avgRed, avgGreen, avgBlue;
				
				int tempRed = fullImage[i][j].getRed();
				int tempGreen = fullImage[i][j].getGreen();
				int tempBlue = fullImage[i][j].getBlue();
				
				int k;
				for (k = 1; k < blurLength; k++) {
					
					if ((j + k) >= width){
						break;
					}
					else
					{
						tempRed += fullImage[i][j+k].getRed();
						tempGreen += fullImage[i][j+k].getGreen();
						tempBlue += fullImage[i][j+k].getBlue();
					}
				}
				
				avgRed = tempRed/k;
				avgGreen = tempGreen/k;
				avgBlue = tempBlue/k;
				
				fullImage[i][j].setRed(avgRed);
				fullImage[i][j].setGreen(avgGreen);
				fullImage[i][j].setBlue(avgBlue);
				
			}
		}
	}

	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("P3\n");
		str.append(width + " " + height + "\n");
		str.append(maxColorValue + "\n");

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				str.append(fullImage[i][j].toString());
			}
		}
		return str.toString();
	}

}
