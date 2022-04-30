$(function() {

    //Wait for Pinegrow to wake-up
    $("body").one("pinegrow-ready", function(e, pinegrow) {

        //Create new Pinegrow framework object
        var f = new PgFramework("UserLib", "UserLib");

        //This will prevent activating multiple versions of this framework being loaded
        f.type = "UserLib";
        f.allow_single_type = true;
        f.user_lib = true

        var comp_comp1 = new PgComponentType('comp1', 'Comp 1 / Jumbotron');
        comp_comp1.code = '<div class="jumbotron">\
    <div class="container">\
        <h1 class="display-3">Hello, world!</h1>\
        <p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of fragment. Use it as a starting point to create something more unique.</p>\
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more »</a></p>\
    </div>\
</div>';
        comp_comp1.parent_selector = null;
        f.addComponentType(comp_comp1);
        
        //Tell Pinegrow about the framework
        pinegrow.addFramework(f);
            
        var section = new PgFrameworkLibSection("UserLib_lib", "Components");
        //Pass components in array
        section.setComponentTypes([comp_comp1]);

        f.addLibSection(section);
   });
});