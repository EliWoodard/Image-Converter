# Image Converter

This is a simple Java program that can be used to convert images to black and white. It can be used to convert a single image or all the images in a folder.

## Usage

To use the program, navigate to the directory containing the Java files and run the following command:

By default, this will convert all the images in the `Convert-Images` folder to black and white and save them in the `Convert-Images-BW` folder as JPEG files. It will also display a progress bar to show the user the progress of the conversion process.

You can and should change the file type your working with in the code. This will be improved upon in the future. 

### Arguments

The program accepts the following arguments:

- `-delete`: Deletes all the files in the `Convert-Images` and `Convert-Images-BW` folders.

- `-format [format]`: Specifies the output image format. Supported formats include `jpg`, `png`, and `bmp`. Example usage: `java Main -format png`.

- `-noprogress`: Disables the progress bar.

- `[inputPath] [outputPath] [outputFormat] [showProgressBar]`: Allows the user to specify the input folder, output folder, output format, and whether to show the progress bar. Example usage: `java Main Convert-Images My-Converted-Images png true`.

If no arguments are provided, the program will convert all the images in the `Convert-Images` folder to black and white and save them in the `Convert-Images-BW` folder as JPEG files. It will also display a progress bar to show the user the progress of the conversion process.

## Limitations

- The program only supports the conversion of JPEG, PNG, and BMP files.

- The program assumes that all files in the input folder are images. Non-image files will be skipped.

- The program overwrites existing files in the output folder with the same name.

- The progress bar may not display correctly on some operating systems.

## Possible Future Improvements

- Add support for more image formats.

- Allow the user to specify the output file name.

- Improve error handling to handle more user errors.

- Add support for multi-threaded conversion to improve performance.

- Allow the user to resize the images during the conversion process. [x]

- Add support for additional image manipulation, such as rotation and cropping.

- Add a graphical user interface to make the program more user-friendly.

- Allow the user to cancel the conversion process.

- Add support for conversion to color spaces other than grayscale.

- Improve the progress bar to display estimated time remaining and other statistics.

- Image resizing: Allow users to resize images during the conversion process to specific dimensions or a percentage of their original size. This could be useful for reducing the file size of large images or for standardizing the dimensions of a batch of images.

- Batch renaming: Allow users to rename a batch of images during the conversion process with a common prefix or suffix, followed by a sequential number.

- Output quality control: Allow users to specify the output image quality, compression level, and other settings to control the file size and visual quality of the converted images.

- Multithreading: Implement multithreading to speed up the conversion process for large batches of images by processing multiple images simultaneously.

- GUI interface: Develop a graphical user interface for the image converter that allows users to drag and drop images, preview the converted images, and easily adjust conversion settings.

## Acknowledgments

This program was created by Eli Woodard.
