(ns org.questionsquestions.image
  (:import [java.awt Color Font FontMetrics Graphics2D RenderingHints]
           [java.awt.image BufferedImage]
           [java.io File IOException ByteArrayOutputStream]
           [javax.imageio ImageIO]))

(def size [1200 628])
(def w (first size))
(def h (second size))

(def rendering_hints
  [[RenderingHints/KEY_ALPHA_INTERPOLATION RenderingHints/VALUE_ALPHA_INTERPOLATION_QUALITY]
   [RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON]
   [RenderingHints/KEY_COLOR_RENDERING RenderingHints/VALUE_COLOR_RENDER_QUALITY]
   [RenderingHints/KEY_DITHERING RenderingHints/VALUE_DITHER_ENABLE]
   [RenderingHints/KEY_FRACTIONALMETRICS RenderingHints/VALUE_FRACTIONALMETRICS_ON]
   [RenderingHints/KEY_INTERPOLATION RenderingHints/VALUE_INTERPOLATION_BILINEAR]
   [RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY]
   [RenderingHints/KEY_STROKE_CONTROL RenderingHints/VALUE_STROKE_PURE]])

(def font
  (Font. "Arial", Font/PLAIN 72))

(defn bufferedimage->bytes [bi]
  (let [os (ByteArrayOutputStream.)
        _ (ImageIO/write bi "jpg" os)]
    (.toByteArray os)))

(defn forString [s]
  (let [img (BufferedImage. w h BufferedImage/TYPE_INT_ARGB)
        gd (doto
               (.createGraphics img)
             (#(doseq [[k v] rendering_hints]
                 (.setRenderingHint % k v)))
             (.setFont font)
             (.setColor Color/BLACK))
        ascent (.getAscent (.getFontMetrics gd))
        _ (doto gd
            (.drawString s 0 ascent)
            (.dispose))]
    (bufferedimage->bytes img)))
