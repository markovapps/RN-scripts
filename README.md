
# Add as submodule
git submodule add git@github.com:markovapps/RN-scripts.git scripts

# then add to package.json
```shell
"scripts": {
    "bump:ios": "npx tsx scripts/bump-ios-build.ts",
    "bump:android": "npx tsx scripts/bump-android-build.ts",
    "build:ios": "npm run bump:ios && npx eas-cli build --platform ios --local",
    "build:android": "npm run bump:android && npx eas-cli build --platform android --local",
    "build:ios:cloud": "npm run bump:ios && npx eas-cli build --platform ios",
    "build:android:cloud": "npm run bump:android && npx eas-cli build --platform android",
    "submit:ios": "npx tsx scripts/ios-submit.ts"
}
```