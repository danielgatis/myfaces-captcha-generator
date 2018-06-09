package myfaces.captcha.generator;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.captcha.util.CAPTCHAImageGenerator;
import org.apache.myfaces.custom.captcha.util.CAPTCHATextGenerator;
import org.apache.myfaces.custom.captcha.util.ColorGenerator;
import org.springframework.mock.web.MockHttpServletResponse;

import com.vdurmont.etaprinter.ETAPrinter;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "java -jar generate")
public class App implements Runnable {
	@Parameters(index = "0", description="number of captchas to generate.")
	private int number = 1;

	@Parameters(index = "1", description="output path.")
	private String path = ".";

	public void run() {
		int i = 0;
		ETAPrinter printer = ETAPrinter.init(number);
		new File(path).mkdirs();

		while (i < number) {
			MockHttpServletResponse response = new MockHttpServletResponse();
			String captchaText = CAPTCHATextGenerator.generateRandomText();

			if (captchaText.length() < 6) {
				continue;
			}

			Color endingColor = ColorGenerator.generateRandomColor(null);
			Color startingColor = ColorGenerator.generateRandomColor(endingColor);
			CAPTCHAImageGenerator imageGenerator = new CAPTCHAImageGenerator();

			try {
				imageGenerator.generateImage(response, captchaText, startingColor, endingColor);
				FileUtils.writeByteArrayToFile(new File(FilenameUtils.concat(path, captchaText + ".jpg")),
						response.getContentAsByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}

			i++;
			printer.update(1);
		}
	}

	public static void main(String[] args) {
		CommandLine.run(new App(), System.out, args);
	}
}