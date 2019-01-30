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

import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class ImageTransformUtils {
	public static BufferedImage convertToGrayscale(BufferedImage input){
		BufferedImage output = new BufferedImage(input.getWidth(),input.getHeight(),input.getType());
		ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
		op.filter(input,output);
		return output;
	}

	public static BufferedImage getTransformedImage(BufferedImage image,double scaleX,double scaleY,double shearX,double shearY){
		AffineTransform transform = new AffineTransform();
		if ( scaleX > 0 && scaleY > 0 )
			transform.scale(scaleX, scaleY);
		if ( shearX > 0 && shearY > 0 )
			transform.shear(shearX, shearY);
		
		AffineTransformOp op = new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
		BufferedImage dest = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
		op.filter(image, dest);
		return dest;
	}
	
	public static void main(String args[]){
		try {
			//ImageTransformUtils.generateIntegralImage(ImageIO.read(new File("/data/work/OpenSURF/Images/img1.jpg")));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}