/*
This work was derived from Chris Evan's opensurf project and re-licensed as the
3 clause BSD license with permission of the original author. Thank you Chris! 

Copyright (c) 2010, Andrew Stromberg
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither Andrew Stromberg nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Andrew Stromberg BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package reconocimientoSURF;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * A wrapper around BufferedImage which makes testing of IntegralImage considerably simpler.
 */
public class ImageWrapper {
  private final int height;
  private final int width;
  private final WritableRaster raster;
  // Generally used for tests.
  private final int[][][] pixelData;
  
  public ImageWrapper(BufferedImage image) {
    height = image.getHeight();
    width = image.getWidth();
    raster = image.getRaster();
    pixelData = null;
  }

  public ImageWrapper(int[][][] image) {
    height = image.length;
    width = image[0].length;
    pixelData = image;
    raster = null;
  }

  public int getHeight() {
    return height;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int[] getPixelAt(int x, int y) {
    if (raster != null) {
      int[] pixel = new int[4];
      raster.getPixel(x, y, pixel);
      return pixel;
    }
    return pixelData[y][x];
  }
}