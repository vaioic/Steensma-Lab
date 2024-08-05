def annoList = getAnnotationObjects()
def anno = annoList.get(0)
def tissue = anno.getROI()
def tissueClass = getPathClass('Tissue')
def roiClass = getPathClass('ROI')
def tissue_area = tissue.getArea()
double twenty_percent = tissue_area*0.2
double ROI_area = 2048*2048
double num_ROIs = Math.round(twenty_percent/ROI_area)
anno.each{it.setPathClass(tissueClass)}
println num_ROIs
def random_ROIs = []

for (i in 1..num_ROIs) {
    randomROI = RoiTools.createRandomRectangle(tissue, 2048, 2048)
    randomAnnot = PathObjects.createAnnotationObject(randomROI)
    random_ROIs.add(randomAnnot)
    tissue = RoiTools.difference(tissue, randomROI)
    //println tissue.getArea()
}

addObjects(random_ROIs)
def ROIs = getAnnotationObjects().findAll{it.getPathClass() == null}
ROIs.each{it.setPathClass(roiClass)}

ROIs = selectObjectsByClassification('ROI')

createDetectionsFromPixelClassifier("Adipocytes", 200.0, 200.0, "SPLIT");
selectDetections();
runPlugin('qupath.lib.algorithms.IntensityFeaturesPlugin', '{"pixelSizeMicrons":0.5,"region":"ROI","tileSizeMicrons":25.0,"colorOD":true,"colorStain1":false,"colorStain2":false,"colorStain3":false,"colorRed":true,"colorGreen":true,"colorBlue":true,"colorHue":false,"colorSaturation":true,"colorBrightness":true,"doMean":true,"doStdDev":true,"doMinMax":true,"doMedian":true,"doHaralick":true,"haralickDistance":1,"haralickBins":32}');
addShapeMeasurements("AREA", "LENGTH", "CIRCULARITY", "SOLIDITY", "MAX_DIAMETER", "MIN_DIAMETER");
runObjectClassifier("Adipocytes");