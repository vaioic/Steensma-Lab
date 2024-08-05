selectAnnotations();
runPlugin('qupath.lib.algorithms.TilerPlugin', '{"tileSizeMicrons":200.0,"trimToROI":true,"makeAnnotations":false,"removeParentAnnotation":false}')
selectTiles();
runPlugin('qupath.lib.algorithms.IntensityFeaturesPlugin', '{"pixelSizeMicrons":0.5,"region":"SQUARE","tileSizeMicrons":200.0,"colorOD":true,"colorStain1":true,"colorStain2":true,"colorStain3":true,"colorRed":true,"colorGreen":true,"colorBlue":true,"colorHue":true,"colorSaturation":true,"colorBrightness":true,"doMean":true,"doStdDev":true,"doMinMax":true,"doMedian":true,"doHaralick":true,"haralickDistance":1,"haralickBins":32}')
addPixelClassifierMeasurements("Mammary_Gland_Pixel_Classifier_V4", "Mammary_Gland_Pixel_Classifier_V4")
classifyDetectionsByCentroid("Mammary_Gland_Pixel_Classifier_V4")