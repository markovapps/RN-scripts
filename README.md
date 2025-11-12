
 
```shell
# Add as submodule
git submodule add git@github.com:markovapps/RN-scripts.git scripts

# run
jbang --fresh scripts/bump_ios_build.java && npx eas-cli build --platform ios
jbang --fresh scripts/submit_ios.java --path 

```

Add to package.json
```json
"scripts": {
    "bump:ios": "jbang --fresh scripts/bump_ios_build.java",
    "bump:android": "jbang --fresh scripts/bump_android_build.java",
    "build:ios": "npm run bump:ios && npx eas-cli build --platform ios --local",
    "build:android": "npm run bump:android && npx eas-cli build --platform android --local",
    "build:ios:cloud": "npm run bump:ios && npx eas-cli build --platform ios ",
    "build:android:cloud": "npm run bump:android && npx eas-cli build --platform android",
    "submit:ios": "jbang --fresh scripts/submit_ios.java"
}
```