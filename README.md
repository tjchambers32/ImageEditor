Image Editor
===============

A simple PPM-format image editor.

##Introduction##
This is a rudimentary image editor that can handle 4 transformations on a PPM image.

###Transformations###
  * Invert Colors
  * Convert to Grayscale
  * Generate an "embossed" image
  * Add a "motion blur" effect

##The Portable PixelMap (PPM) File Format##
PPM files were chosen for their simplicity.

The format of a PPM is as follows:
  * Each PPM file begins with a "P3"
  * Whitespace
  * A width, formatted as ASCII characters in decimal
  * Whitespace
  * A height, formatted as ASCII characters in decimal
  * Whitespace
  * Maximum color value. (255 for our files)
  * Whitespace
  * Sets of 3 numbers from 0-255, each representing an RGB color value, the first representing the red value, the second representing the green value, and the third representing the blue value. Each color value is separated by whitespace. A set of 3 color values represents a pixel.

##Usage##
```
java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)
```
