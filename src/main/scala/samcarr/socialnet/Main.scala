package samcarr.socialnet

object Main {

  def main(args: Array[String]): Unit = {
    val net = SocialNet()
    val cli = new Cli()
    cli.session(net)
  }
}