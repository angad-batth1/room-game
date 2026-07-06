package utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * This is the asset loader class that caches and loads image assets from the filesystem or bundled resources.
 * @author Gurangad Batth
 */
public final class AssetLoader {
    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();

    private AssetLoader(){
    }

    /**
     * This method loads an image asset and keeps it cached for future use.
     * @param filepath the path to the image file
     * @return the loaded image, or null if the image could not be loaded
     */
    public static BufferedImage loadImage(String filepath){
        if(IMAGE_CACHE.containsKey(filepath)){
            return IMAGE_CACHE.get(filepath);
        }

        BufferedImage image = readImage(filepath);
        IMAGE_CACHE.put(filepath, image);
        return image;
    }

    /**
     * This helper method loads the raw image data from disk first and then from bundled resources.
     * @param filepath the path to the image file
     * @return the loaded image, or null if loading fails
     */
    private static BufferedImage readImage(String filepath){
        try{
            Path path = Path.of(filepath);
            if(Files.exists(path)){
                return trimTransparentBorders(ImageIO.read(path.toFile()));
            }

            InputStream inputStream = AssetLoader.class.getClassLoader().getResourceAsStream(filepath);
            if(inputStream == null && filepath.startsWith("assets/")){
                inputStream = AssetLoader.class.getClassLoader().getResourceAsStream(filepath.substring("assets/".length()));
            }

            if(inputStream != null){
                try(InputStream safeInputStream = inputStream){
                    return trimTransparentBorders(ImageIO.read(safeInputStream));
                }
            }
        }catch(IOException e){
            System.out.println("failed to load image asset: " + filepath);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This helper method trims empty transparent padding around sprite style images.
     * @param image the loaded image
     * @return a cropped image if transparent borders exist, otherwise the original image
     */
    private static BufferedImage trimTransparentBorders(BufferedImage image){
        if(image == null){
            return null;
        }

        ColorModel colorModel = image.getColorModel();
        if(!colorModel.hasAlpha()){
            return image;
        }

        int minX = image.getWidth();
        int minY = image.getHeight();
        int maxX = -1;
        int maxY = -1;

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int alpha = (image.getRGB(x, y) >>> 24) & 0xff;
                if(alpha > 8){
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        if(maxX < minX || maxY < minY){
            return image;
        }

        return image.getSubimage(minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }
}
