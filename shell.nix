{ jdk ? "jdk11" }:

let
  pkgs = import <nixpkgs> { inherit jdk; };
in
  pkgs.mkShell {
    name = "roguescave";

    buildInputs = [
      pkgs.coursier
      pkgs.${jdk}
      pkgs.sbt
      pkgs.mill
      pkgs.electron
      pkgs.nodePackages.http-server
      pkgs.nodejs-14_x
      pkgs.yarn
    ];
  }
