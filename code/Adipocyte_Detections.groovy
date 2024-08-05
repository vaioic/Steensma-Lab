selectAnnotations();
createDetectionsFromPixelClassifier("Adipocytes", 200.0, 200.0, "SPLIT");
selectDetections();
runPlugin('qupath.lib.algorithms.IntensityFeaturesPlugin', '{"pixelSizeMicrons":0.5,"region":"ROI","tileSizeMicrons":25.0,"colorOD":true,"colorStain1":false,"colorStain2":false,"colorStain3":false,"colorRed":true,"colorGreen":true,"colorBlue":true,"colorHue":false,"colorSaturation":true,"colorBrightness":true,"doMean":true,"doStdDev":true,"doMinMax":true,"doMedian":true,"doHaralick":true,"haralickDistance":1,"haralickBins":32}');
addShapeMeasurements("AREA", "LENGTH", "CIRCULARITY", "SOLIDITY", "MAX_DIAMETER", "MIN_DIAMETER");
runObjectClassifier("Adipocytes");