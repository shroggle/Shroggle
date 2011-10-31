This is FLVmeta, a metadata injector for Adobe(r) FLV video files.

The package contains two programs:
- flvdump, an utility to print all informations about an input FLV file,
- flvmeta, an utility to inject an onMetaData event in FLV files, as well as
the onLastSecond event.

The flvmeta utility is able to compute almost all known metadata tags,
including the keyframes list.
Since version 1.0.7, it is also able to fix very large FLV files with
invalid 24-bit only timestamps to make proper use of 32-bit extended
timestamps.

The main goal of this program is to write an alternative to tools like flvtool2.
It is written in portable C, and should compile on most platforms (it has
been successfully compiled and tested on Ubuntu Feisty, MacOSX 10.4, and
Windows XP).
FLVmeta is fast and has a very small memory footprint.


FLVmeta is provided "as is" with no warranty.  The exact terms
under which you may use and (re)distribute this program are detailed
in the GNU General Public License, in the file COPYING.

See the files AUTHORS and THANKS for a list of authors and other contributors.

See the file INSTALL for compilation and installation instructions.

See the file NEWS for a description of major changes in this release.

See the file

Send bug reports to flvmeta-discussion@googlegroups.com.
