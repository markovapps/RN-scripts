
 
```shell
# Add as submodule
git submodule add git@github.com:markovapps/RN-scripts.git scripts

# run
jbang --fresh scripts/bump_ios_build.java && npx eas-cli build --platform ios
jbang --fresh scripts/submit_ios.java --path 

```

npx Add to package.json
```json
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