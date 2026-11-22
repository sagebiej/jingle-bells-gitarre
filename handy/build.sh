#!/bin/bash
# Build the phone app (WebView wrapper, loads GitHub Pages, bundled ../index.html offline) with the Ubuntu android-sdk packages.
#   ./build.sh            build jinglebells-handy.apk
#   ./build.sh install    also install it on the phone connected by USB (adb)
set -e
cd "$(dirname "$0")"
SDK=/usr/lib/android-sdk
JAR=$SDK/platforms/android-23/android.jar
KS=${KEYSTORE:-$HOME/.local/share/jinglebells-tv/release.keystore}
rm -rf build res assets && mkdir -p build/classes assets
cp ../index.html assets/index.html
python3 -I ../firetv/make_images.py "$PWD"
javac -nowarn --release 8 -cp "$JAR" -d build/classes $(find src -name '*.java')
$SDK/build-tools/29.0.3/dx --dex --min-sdk-version=23 --output=build/classes.dex build/classes
aapt package -f -M AndroidManifest.xml -S res -A assets -I "$JAR" -F build/unsigned.apk
(cd build && aapt add -f unsigned.apk classes.dex >/dev/null)
zipalign -f 4 build/unsigned.apk build/aligned.apk
if [ ! -f "$KS" ]; then
    mkdir -p "$(dirname "$KS")"
    keytool -genkeypair -keystore "$KS" -storepass jinglebells -keypass jinglebells \
        -alias jb -keyalg RSA -keysize 2048 -validity 20000 -dname "CN=jinglebells" >/dev/null 2>&1
fi
apksigner sign --ks "$KS" --ks-pass pass:jinglebells --out jinglebells-handy.apk build/aligned.apk
echo "built $PWD/jinglebells-handy.apk"
if [ "$1" = install ]; then
    adb install -r jinglebells-handy.apk
fi
